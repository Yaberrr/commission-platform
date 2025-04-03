package com.moe.order.handler.impl;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.exception.ServiceException;
import com.moe.order.handler.OrderHandler;
import org.springframework.stereotype.Component;

/**
 * 已到账订单
 * @author tangyabo
 * @date 2025/4/3
 */
@Component
public class AccountedOrderHandler implements OrderHandler {

    @Override
    public void handleStatusChange(Order order, OrderStatus newStatus) {
        throw new ServiceException("已到账订单状态不可修改");
    }
}
