package com.moe.common.core.enums.config;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 全局配置类型
 */
@Getter
public enum GlobalConfigType {

    CUSTOMER_QR_CODE(1, "客服二维码"),
    INVITATION_TEMPLATE(2, "邀请模板"),
    MEMBER_GROWTH(3, "会员成长");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    GlobalConfigType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static GlobalConfigType fromCode(int code) {
        for (GlobalConfigType e : GlobalConfigType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
