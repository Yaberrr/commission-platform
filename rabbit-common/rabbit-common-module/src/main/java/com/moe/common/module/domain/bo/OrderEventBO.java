package com.moe.common.module.domain.bo;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.order.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单事件
 * @author tangyabo
 * @date 2025/4/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventBO {

    private Order order;

    private OrderEvent event;

}
