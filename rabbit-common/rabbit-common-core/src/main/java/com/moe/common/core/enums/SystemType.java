package com.moe.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;


/**
 * 系统类型
 */
@Getter
public enum SystemType {

    ADMIN(1, "后台"),
    APP(2, "app");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    SystemType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static SystemType fromCode(int code) {
        for (SystemType e : SystemType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
