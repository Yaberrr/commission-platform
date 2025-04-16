package com.moe.common.core.enums.order;

import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

/**
 * 订单变化事件
 * @author tangyabo
 * @date 2025/4/16
 */
@Getter
public enum OrderEvent {

    NEW_TO_PAID(null,OrderStatus.PAID),
    NEW_TO_RECEIVED(null,OrderStatus.RECEIVED),
    NEW_TO_ACCOUNTED(null,OrderStatus.ACCOUNTED),
    NEW_TO_EXPIRED(null,OrderStatus.EXPIRED),
    PAID_TO_RECEIVED(OrderStatus.PAID,OrderStatus.RECEIVED),
    PAID_TO_ACCOUNTED(OrderStatus.PAID,OrderStatus.ACCOUNTED),
    PAID_TO_EXPIRED(OrderStatus.PAID,OrderStatus.EXPIRED),
    RECEIVED_TO_ACCOUNTED(OrderStatus.RECEIVED,OrderStatus.ACCOUNTED),
    RECEIVED_TO_EXPIRED(OrderStatus.RECEIVED,OrderStatus.EXPIRED);


    private final OrderStatus oldStatus;

    private final OrderStatus newStatus;

    OrderEvent(OrderStatus oldStatus, OrderStatus newStatus) {
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public static OrderEvent getEvent(OrderStatus oldStatus, OrderStatus newStatus){
        for (OrderEvent event : values()) {
            if (Objects.equals(event.oldStatus, oldStatus) && event.newStatus == newStatus) {
                return event;
            }
        }
        return null;
    }
}
