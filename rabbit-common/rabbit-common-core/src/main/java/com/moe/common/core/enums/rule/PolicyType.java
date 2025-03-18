package com.moe.common.core.enums.rule;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum PolicyType {

    PrivacyPolicy(1,"隐私政策"),
    UserAgreement(2,"用户协议"),
    PersonalInformationCollectionList(3,"个人信息收集清单"),
    ThirdPartyServiceIntegrationAgreement(4,"第三方服务介入协议");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    PolicyType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static PolicyType fromCode(int code) {
        for (PolicyType e : PolicyType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
