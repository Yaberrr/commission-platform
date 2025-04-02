package com.moe.order.domain.bo;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 订单状态bo
 * @author tangyabo
 * @date 2025/4/2
 */
@Data
@AllArgsConstructor
public class OrderStatusBO {

    private Order order;

    //旧状态
    private OrderStatus oldStatus;

    //新状态
    private OrderStatus newStatus;
}
