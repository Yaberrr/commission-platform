package com.moe.order.handler.impl;


import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.order.OrderStatus;
import com.moe.order.handler.OrderHandler;
import com.moe.order.service.ICommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 已收货订单
 * @author tangyabo
 * @date 2025/4/3
 */
@Component
public class PaidOrderHandler implements OrderHandler {

    @Autowired
    private ICommissionService commissionService;

    @Override
    public void handleStatusChange(Order order, OrderStatus newStatus) {
        switch (newStatus){
            case ACCOUNTED:
                commissionService.commissionToAccount(order);
                break;
            case EXPIRED:
                commissionService.deductCommission(order);
                break;
            case RECEIVED:
        }
    }
}
