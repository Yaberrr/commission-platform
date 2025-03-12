package com.moe.common.core.enums.message;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息类型
 */
@Getter
public enum MessageType {

    ORDER(1, "订单"),
    YUAN_BAO(2, "元宝宝");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    MessageType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static MessageType fromCode(int code) {
        for (MessageType e : MessageType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
