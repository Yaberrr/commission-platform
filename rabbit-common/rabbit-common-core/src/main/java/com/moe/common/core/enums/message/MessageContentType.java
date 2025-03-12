package com.moe.common.core.enums.message;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息格式
 */
@Getter
public enum MessageContentType {

    TOP_BOTTOM_IMAGE_TEXT(1, "上下图文"),
    LEFT_RIGHT_IMAGE_TEXT(2, "左右图文"),
    PLAIN_TEXT(3, "纯文字");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    MessageContentType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static MessageContentType fromCode(int code) {
        for (MessageContentType e : MessageContentType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
