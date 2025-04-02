package com.moe.order.service;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.platform.PlatformType;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/2
 */
public interface IOrderService {

    /**
     * 批量更新订单
     * @param platformType
     * @param orderList
     * @return
     */
    int batchUpdateOrder(PlatformType platformType, List<Order> orderList);


}
