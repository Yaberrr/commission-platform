package com.moe.common.core.enums.config;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 用户配置类型
 */
@Getter
public enum UserConfigType {

    HIDE_ORDER_INFO(1, "隐藏我的订单信息", "0");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    //默认值
    private final String defaultValue;

    UserConfigType(int code, String desc, String defaultValue) {
        this.code = code;
        this.desc = desc;
        this.defaultValue = defaultValue;
    }

    @JsonCreator
    public static UserConfigType fromCode(Integer code){
        if(code == null) return null;
        for (UserConfigType e : UserConfigType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
