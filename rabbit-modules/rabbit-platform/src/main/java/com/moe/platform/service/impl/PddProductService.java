package com.moe.platform.service.impl;

import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.dto.PlatformProductDTO;
import com.moe.platform.dto.PlatformParam;
import com.moe.platform.convert.PddConvert;
import com.moe.platform.service.PlatformProductService;
import com.moe.platform.vo.ProductVO;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(PddProductService.class);

    @Autowired
    private PopClient popClient;

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
            return this.invokeRequest(request);
        }catch (Exception e){
            log.error("拼多多商品查询api异常",e);
            return TableDataInfo.error();
        }
    }


    /**
     * 调用api
     */
    private TableDataInfo<ProductVO> invokeRequest(PddDdkGoodsSearchRequest request) throws Exception {
        PddDdkGoodsSearchResponse response = popClient.syncInvoke(request);
        if(response.getErrorResponse() != null){
            throw new ServiceException(response.getErrorResponse().getErrorMsg());
        }
        //提取数据
        PddDdkGoodsSearchResponse.GoodsSearchResponse searchResponse = response.getGoodsSearchResponse();
        List<ProductVO> productVOList = searchResponse.getGoodsList()
                .stream().map(PddConvert::toProductVO).collect(Collectors.toList());
        return new TableDataInfo<>(productVOList,searchResponse.getTotalCount());
    }
}
