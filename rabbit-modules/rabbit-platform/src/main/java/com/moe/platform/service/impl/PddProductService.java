package com.moe.platform.service.impl;

import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.convert.PddConvert;
import com.moe.platform.dto.PlatformParam;
import com.moe.platform.dto.PlatformProductDTO;
import com.moe.platform.dto.PlatformProductDetailDTO;
import com.moe.platform.dto.PlatformSearchDTO;
import com.moe.platform.service.PlatformAuthService;
import com.moe.platform.service.PlatformProductService;
import com.moe.platform.utils.PddUtils;
import com.moe.platform.utils.PlatformUtils;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsDetailRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsDetailResponse;
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
public class PddProductService implements PlatformProductService {

    @Autowired
    private PopClient popClient;
    @Autowired
    private PlatformUtils platformUtils;
    @Autowired
    private PlatformAuthService platformAuthService;


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
            //可选授权信息，用于判断比价
            platformUtils.getPlatformAuth(PlatformType.PDD, platformAuthService)
                    .ifPresent(auth -> {
                        request.setPid(auth.getAuthId());
                        request.setCustomParameters(PddUtils.getCustomParameter(auth));
                    });

            return this.invokeRequest(request);
        }catch (Exception e){
            throw new ServiceException(e,"拼多多商品查询失败:{}",e.getMessage());
        }
    }

    @Override
    public TableDataInfo<ProductVO> productSearch(PlatformSearchDTO dto) {
        try {
            PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();
            //必须授权
            PlatformAuth auth = platformUtils.checkPlatformAuth(PlatformType.PDD, platformAuthService);
            request.setPid(auth.getAuthId());
            request.setCustomParameters(PddUtils.getCustomParameter(auth));
            request.setKeyword(dto.getKeyword());
            request.setPage(dto.getPageNum());
            request.setPageSize(dto.getPageSize());
            return this.invokeRequest(request);
        }  catch (Exception e){
            throw new ServiceException(e,"拼多多商品搜索失败:{}",e.getMessage());
        }
    }

    /**
     * 调用商品搜索api
     */
    private TableDataInfo<ProductVO> invokeRequest(PddDdkGoodsSearchRequest request) throws Exception {
        PddDdkGoodsSearchResponse response = popClient.syncInvoke(request);
        PddUtils.checkResponse(response);
        //提取数据
        PddDdkGoodsSearchResponse.GoodsSearchResponse searchResponse = response.getGoodsSearchResponse();
        List<ProductVO> productVOList = searchResponse.getGoodsList()
                .stream().map(PddConvert::toProductVO).collect(Collectors.toList());
        return new TableDataInfo<>(productVOList,searchResponse.getTotalCount());
    }

    @Override
    public ProductDetailVO productDetail(PlatformProductDetailDTO dto){
        try {
            PddDdkGoodsDetailRequest request = new PddDdkGoodsDetailRequest();
            request.setGoodsSign(dto.getProductId());
            //可选授权信息，判断比价
            platformUtils.getPlatformAuth(PlatformType.PDD, platformAuthService)
                    .ifPresent(auth -> {
                        request.setPid(auth.getAuthId());
                        request.setCustomParameters(PddUtils.getCustomParameter(auth));
                    });
            PddDdkGoodsDetailResponse response = popClient.syncInvoke(request);
            PddUtils.checkResponse(response);
            //提取数据
            return PddConvert.toProductDetailVO(response.getGoodsDetailResponse().getGoodsDetails().get(0));
        }catch (Exception e){
            throw new ServiceException(e,"拼多多商品详情查询失败:{}",e.getMessage());
        }
    }



}
