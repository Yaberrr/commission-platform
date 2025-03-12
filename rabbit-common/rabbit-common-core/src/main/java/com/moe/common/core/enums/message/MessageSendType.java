package com.moe.common.core.enums.message;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息发送人群类型
 */
@Getter
public enum MessageSendType {

    ALL_USERS(1, "全体用户"),
    SPECIFIED_USERS(2, "指定用户");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    MessageSendType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static MessageSendType fromCode(int code) {
        for (MessageSendType e : MessageSendType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
