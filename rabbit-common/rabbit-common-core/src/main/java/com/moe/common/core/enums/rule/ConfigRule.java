package com.moe.common.core.enums.rule;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ConfigRule {

    CustomerServiceQRCode(1,"客服二维码"),
    InvitationTemplate(2,"邀请模板"),
    MembershipGrowth(3,"会员成长");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    ConfigRule(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static ConfigRule fromCode(Integer code){
        if(code == null) return null;
        for (ConfigRule e : ConfigRule.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
