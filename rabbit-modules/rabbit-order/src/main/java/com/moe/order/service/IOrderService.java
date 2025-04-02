package com.moe.order.service;

import com.moe.common.core.domain.order.Order;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/2
 */
public interface IOrderService {

    /**
     * 批量更新订单
     * @param orderList
     * @return
     */
    int updateOrder(List<Order> orderList);

}
