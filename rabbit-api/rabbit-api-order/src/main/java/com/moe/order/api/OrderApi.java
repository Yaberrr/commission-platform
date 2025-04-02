package com.moe.order.api;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.order.Order;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
@FeignResponseCheck(serviceName = "订单")
@FeignClient(path = "/orderInner", contextId = "orderApi", value = ServiceNameConstants.ORDER_SERVICE)
public interface OrderApi {

    /**
     * 更新订单信息
     * @param orderList
     * @return
     */
    R<Integer> updateOrder(List<Order> orderList);

}
