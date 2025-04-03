package com.moe.order.handler;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.order.OrderStatus;

/**
 * 订单状态处理器
 * @author tangyabo
 * @date 2025/4/3
 */
public interface OrderHandler {

    /**
     * 处理状态变化
     * @param order 原订单
     * @param newStatus 新状态
     */
    void handleStatusChange(Order order, OrderStatus newStatus);

}
