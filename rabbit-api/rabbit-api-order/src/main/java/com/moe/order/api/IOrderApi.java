package com.moe.order.api;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.platform.PlatformType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
@FeignResponseCheck(serviceName = "订单")
@FeignClient(path = "/orderInner", contextId = "orderApi", value = ServiceNameConstants.ORDER_SERVICE)
public interface IOrderApi {

    /**
     * 批量更新订单
     * @param orderList
     * @return
     */
    @PostMapping("/batchUpdateOrder")
    R<Integer> batchUpdateOrder(PlatformType platformType, List<Order> orderList);

}
