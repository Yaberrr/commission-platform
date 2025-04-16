package com.moe.wallet.service;

import com.moe.common.core.domain.order.Order;

/**
 * 订单佣金
 * @author tangyabo
 * @date 2025/4/2
 */
public interface ICommissionService {

    /**
     * 添加佣金
     * @param order
     */
    void addCommission(Order order);

    /**
     * 佣金到账
     */
    void commissionToAccount(Order order);

    /**
     * 扣除佣金
     * @param order
     */
    void deductCommission(Order order);
}
