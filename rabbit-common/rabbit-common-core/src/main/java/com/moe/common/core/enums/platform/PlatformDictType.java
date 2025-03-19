package com.moe.common.core.enums.platform;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 平台字典类型
 */
@Getter
public enum PlatformDictType {

    CHANNEL(1, "频道"),
    CATEGORY(2, "分类"),
    LABEL(3,"标签");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    PlatformDictType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static PlatformDictType fromCode(int code) {
        for (PlatformDictType e : PlatformDictType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
