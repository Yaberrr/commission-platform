package com.moe.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 平台类型
 */
@Getter
public enum PlatformType {

    PDD(1, "拼多多");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    PlatformType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static PlatformType fromCode(int code) {
        for (PlatformType e : PlatformType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
