package com.moe.order.service;

import com.moe.order.domain.bo.OrderStatusBO;

import java.util.List;

/**
 * 订单佣金
 * @author tangyabo
 * @date 2025/4/2
 */
public interface IOrderCommissionService {

    /**
     * 处理订单状态变化对应的佣金
     */
    void handleCommission(List<OrderStatusBO> statusBOList);

}
