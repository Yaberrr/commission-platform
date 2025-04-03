package com.moe.order.handler.impl;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.order.OrderStatus;
import com.moe.order.handler.OrderHandler;
import com.moe.order.service.IOrderCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 新订单
 * @author tangyabo
 * @date 2025/4/3
 */
@Component
public class NewOrderHandler implements OrderHandler {

    @Autowired
    private IOrderCommissionService orderCommissionService;

    @Override
    public void handleStatusChange(Order order, OrderStatus newStatus) {
        switch (newStatus){
            case PAID:
            case RECEIVED:
                orderCommissionService.addCommission(order);
                break;
            case ACCOUNTED:
                orderCommissionService.addCommission(order);
                orderCommissionService.commissionToAccount(order);
                break;
            case EXPIRED:

        }
    }

}
