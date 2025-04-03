package com.moe.platform.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moe.common.core.exception.ServiceException;
import com.moe.platform.convert.PddConvert;
import com.moe.platform.dto.order.PlatformOrderDTO;
import com.moe.platform.service.IPlatformOrderService;
import com.moe.platform.utils.PddUtils;
import com.moe.platform.vo.PlatformOrderVO;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkOrderListIncrementGetRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkOrderListIncrementGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tangyabo
 * @date 2025/3/31
 */
@Service
public class PddOrderService implements IPlatformOrderService {

    @Autowired
    private PopClient popClient;

    @Override
    public int totalCount(PlatformOrderDTO dto) {
        try {
            PddDdkOrderListIncrementGetRequest request = new PddDdkOrderListIncrementGetRequest();
            request.setPage(10000);
            request.setPageSize(10);
            request.setStartUpdateTime(dto.getStartTime());
            request.setEndUpdateTime(dto.getEndTime());
            request.setReturnCount(true);
            PddDdkOrderListIncrementGetResponse response = popClient.syncInvoke(request);
            PddUtils.checkResponse(response);
            return (int)response.getOrderListGetResponse().getTotalCount().longValue();
        }catch (Exception e){
            throw new ServiceException(e,"查询订单api异常:{}",e.getMessage());
        }
    }

    @Override
    public List<PlatformOrderVO> orderList(PlatformOrderDTO dto) {
        try {
            PddDdkOrderListIncrementGetRequest request = new PddDdkOrderListIncrementGetRequest();
            request.setPage(dto.getPageNum());
            request.setPageSize(dto.getPageSize());
            request.setStartUpdateTime(dto.getStartTime());
            request.setEndUpdateTime(dto.getEndTime());
            //不返回总数，提升效率
            request.setReturnCount(false);
            PddDdkOrderListIncrementGetResponse response = popClient.syncInvoke(request);
            PddUtils.checkResponse(response);
            return response.getOrderListGetResponse().getOrderList().stream()
                    .map(PddConvert::toOrderVO).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException(e,"查询订单api异常:{}",e.getMessage());
        }
    }
}
