package com.moe.common.core.enums.config;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.moe.common.core.domain.config.PlatformConfig;
import lombok.Getter;

/**
 * 平台配置类型
 */
@Getter
public enum PlatformConfigType {

    COMMISSION_RATIO(1, "返佣比例", PlatformConfig.CommissionRatio.class);

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    private final Class<?> jsonClazz;

    PlatformConfigType(int code, String desc, Class<?> jsonClazz) {
        this.code = code;
        this.desc = desc;
        this.jsonClazz = jsonClazz;
    }

    @JsonCreator
    public static PlatformConfigType fromCode(Integer code){
        if(code == null) return null;
        for (PlatformConfigType e : PlatformConfigType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }

}
