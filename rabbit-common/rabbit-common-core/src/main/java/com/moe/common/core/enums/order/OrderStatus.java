package com.moe.common.core.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 订单状态
 */
@Getter
public enum OrderStatus {

    PAID(1, "已付款"),
    RECEIVED(2, "已收货"),
    ACCOUNTED(3, "已到账"),
    EXPIRED(4, "已失效");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static OrderStatus fromCode(Integer code){
        if(code == null) return null;
        for (OrderStatus e : OrderStatus.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
