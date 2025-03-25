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

    PDD(1,"拼多多", true);

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    //是否需要授权
    private final boolean needAuth;

    PlatformType(int code, String desc, boolean needAuth) {
        this.code = code;
        this.desc = desc;
        this.needAuth = needAuth;
    }

    @JsonCreator
    public static PlatformType fromCode(Integer code) {
        if(code == null) return null;
        for (PlatformType e : PlatformType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
