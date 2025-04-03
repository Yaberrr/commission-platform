package com.moe.order.handler.impl;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.exception.ServiceException;
import com.moe.order.handler.OrderHandler;
import org.springframework.stereotype.Component;

/**
 * 已过期订单
 * @author tangyabo
 * @date 2025/4/3
 */
@Component
public class ExpiredOrderHandler implements OrderHandler {

    @Override
    public void handleStatusChange(Order order, OrderStatus newStatus) {
        throw new ServiceException("已过期订单状态不可修改");
    }

}
