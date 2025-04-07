package com.moe.platform.service.impl;

import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.StringUtils;
import com.moe.common.core.utils.bean.BeanCopyUtils;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.convert.PddConvert;
import com.moe.platform.domain.bo.PddGoodsListItemBO;
import com.moe.platform.dto.product.*;
import com.moe.platform.service.IPlatformAuthService;
import com.moe.platform.service.IPlatformProductService;
import com.moe.platform.utils.PddUtils;
import com.moe.platform.utils.PlatformUtils;
import com.moe.platform.vo.PlatformUrlVO;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsDetailRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsPromotionUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsRecommendGetRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsDetailResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsPromotionUrlGenerateResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsRecommendGetResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拼多多商品service
 * @author tangyabo
 * @date 2025/3/19
 */
@Service
public class PddProductService implements IPlatformProductService {

    @Autowired
    private PopClient popClient;
    @Autowired
    private PlatformUtils platformUtils;
    @Autowired
    private IPlatformAuthService platformAuthService;

    @Override
    public TableDataInfo<ProductVO> productList(PlatformProductDTO dto, PlatformParam param) {
        try {
            PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();
            //筛选条件
            if(param.getDictType() != null) {
                switch (param.getDictType()) {
                    case CHANNEL:
                        request.setActivityTags(Collections.singletonList(Integer.valueOf(param.getDictValue())));
                        break;
                    case CATEGORY:
                        request.setCatId(Long.valueOf(param.getDictValue()));
                        break;
                    case LABEL:
                        request.setOptId(Long.valueOf(param.getDictValue()));
                        break;
                }
            }
            request.setPage(dto.getPageNum());
            request.setPageSize(dto.getPageSize());
            //todo: 暂时 只展示有券的
            request.setWithCoupon(true);

            //可选授权信息，判断比价
            PlatformAuth auth = platformUtils.getPlatformAuth(PlatformType.PDD, platformAuthService);
            if(auth != null){
                request.setPid(auth.getAuthId());
                request.setCustomParameters(PddUtils.getCustomParameter(auth));
            }

            return this.invokeSearch(request);
        }catch (Exception e){
            throw new ServiceException(e,"拼多多商品查询失败:{}",e.getMessage());
        }
    }

    @Override
    public TableDataInfo<ProductVO> productSearch(ProductSearchDTO dto) {
        try {
            PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();
            //必须授权
            PlatformAuth auth = platformUtils.checkPlatformAuth(PlatformType.PDD, platformAuthService);
            request.setPid(auth.getAuthId());
            request.setCustomParameters(PddUtils.getCustomParameter(auth));
            request.setKeyword(dto.getKeyword());
            request.setPage(dto.getPageNum());
            request.setPageSize(dto.getPageSize());
            return this.invokeSearch(request);
        }  catch (Exception e){
            throw new ServiceException(e,"拼多多商品搜索失败:{}",e.getMessage());
        }
    }

    /**
     * 调用商品搜索api
     */
    private TableDataInfo<ProductVO> invokeSearch(PddDdkGoodsSearchRequest request) throws Exception {
        PddDdkGoodsSearchResponse response = popClient.syncInvoke(request);
        PddUtils.checkResponse(response);
        //提取数据
         List<ProductVO> productVOList = response.getGoodsSearchResponse().getGoodsList()
                .stream().map(item ->  {
                    PddGoodsListItemBO itemVo = BeanCopyUtils.copy(item, PddGoodsListItemBO.class);
                    return PddConvert.toProductVO(itemVo);
                }).collect(Collectors.toList());
        return new TableDataInfo<>(productVOList,response.getGoodsSearchResponse().getTotalCount());
    }

    @Override
    public ProductDetailVO productDetail(ProductDetailDTO dto){
        try {
            PddDdkGoodsDetailRequest request = new PddDdkGoodsDetailRequest();
            request.setGoodsSign(dto.getProductId());
            request.setSearchId(dto.getSearchParam());

            //可选授权信息，判断比价
            PlatformAuth auth = platformUtils.getPlatformAuth(PlatformType.PDD, platformAuthService);
            if(auth != null){
                request.setPid(auth.getAuthId());
                request.setCustomParameters(PddUtils.getCustomParameter(auth));
            }

            PddDdkGoodsDetailResponse response = popClient.syncInvoke(request);
            PddUtils.checkResponse(response);
            //提取数据
            ProductDetailVO vo = PddConvert.toProductDetailVO(response.getGoodsDetailResponse().getGoodsDetails().get(0));
            vo.setSearchParam(dto.getSearchParam());
            return vo;
        }catch (Exception e){
            throw new ServiceException(e,"拼多多商品详情查询失败:{}",e.getMessage());
        }
    }

    @Override
    public TableDataInfo<ProductVO> productRecommend(ProductRecommendDto dto) {
        try {
            PddDdkGoodsRecommendGetRequest request = new PddDdkGoodsRecommendGetRequest();
            PlatformAuth auth = platformUtils.getPlatformAuth(PlatformType.PDD, platformAuthService);
            if(auth != null){
                //已授权 展示个性化推荐
                request.setChannelType(4);
                request.setPid(auth.getAuthId());
                request.setCustomParameters(PddUtils.getCustomParameter(auth));
            }else {
                //未授权 展示热销爆款
                request.setChannelType(5);
            }

            if(StringUtils.isNotEmpty(dto.getProductId())) {
                //商品相似推荐
                request.setChannelType(3);
                request.setGoodsSignList(Collections.singletonList(dto.getProductId()));
            }

            PddDdkGoodsRecommendGetResponse response = popClient.syncInvoke(request);
            PddUtils.checkResponse(response);
            List<ProductVO> productVOList = response.getGoodsBasicDetailResponse().getList().stream()
                    .map(item -> {
                        PddGoodsListItemBO itemVo = BeanCopyUtils.copy(item, PddGoodsListItemBO.class);
                        return PddConvert.toProductVO(itemVo);
                    }).collect(Collectors.toList());
            return new TableDataInfo<>(productVOList,response.getGoodsBasicDetailResponse().getTotal());
        }catch (Exception e){
            throw new ServiceException(e,"拼多多商品推荐查询失败:{}",e.getMessage());
        }
    }

    @Override
    public PlatformUrlVO productUrl(ProductDetailDTO dto) {
        try {
            PddDdkGoodsPromotionUrlGenerateRequest request = new PddDdkGoodsPromotionUrlGenerateRequest();
            //获取授权信息
            PlatformAuth auth = platformUtils.checkPlatformAuth(PlatformType.PDD, platformAuthService);
            request.setPId(auth.getAuthId());
            request.setGenerateSchemaUrl(true);
            request.setGenerateWeAppLongLink(true);
            request.setCustomParameters(PddUtils.getCustomParameter(auth));
            request.setSearchId(dto.getSearchParam());
            request.setGoodsSignList(Collections.singletonList(dto.getProductId()));
            PddDdkGoodsPromotionUrlGenerateResponse response = popClient.syncInvoke(request);
            PddUtils.checkResponse(response);
            PddDdkGoodsPromotionUrlGenerateResponse.GoodsPromotionUrlGenerateResponseGoodsPromotionUrlListItem item = response.getGoodsPromotionUrlGenerateResponse().getGoodsPromotionUrlList().get(0);
            return new PlatformUrlVO(item.getMobileShortUrl(),item.getSchemaUrl(),item.getWeixinLongLink());
        } catch (Exception e) {
            throw new ServiceException(e,"拼多多商品链接生成失败:{}",e.getMessage());
        }
    }


}
