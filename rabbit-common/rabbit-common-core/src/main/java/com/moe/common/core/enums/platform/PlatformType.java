package com.moe.common.core.enums.platform;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 平台类型
 */
@Getter
public enum PlatformType {

    TAOBAO(1, "淘宝"),
    JINGDONG(2,"京东"),
    PDD(3,"拼多多"),
    TIKTOK(4,"抖音"),
    MEITUAN(5,"美团"),
    WEIPINHUI(6,"唯品会");

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
