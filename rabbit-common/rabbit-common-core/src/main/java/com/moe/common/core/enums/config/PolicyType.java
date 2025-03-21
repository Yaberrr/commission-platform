package com.moe.common.core.enums.config;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 政策类型
 */
@Getter
public enum PolicyType {

    PRIVACY_POLICY(1, "隐私政策"),
    USER_AGREEMENT(2, "用户协议"),
    PERSONAL_INFO_COLLECTION_LIST(3, "个人信息收集清单"),
    THIRD_PARTY_APP_AGREEMENT(4, "第三方应用接入服务协议");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    PolicyType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static PolicyType fromCode(Integer code){
        if(code == null) return null;
        for (PolicyType e : PolicyType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
