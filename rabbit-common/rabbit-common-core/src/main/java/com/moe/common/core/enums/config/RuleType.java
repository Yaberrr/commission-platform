package com.moe.common.core.enums.config;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 规则类型
 */
@Getter
public enum RuleType {

    ORDER_RULE(1, "订单规则"),
    INCOME_RULE(2, "收益规则"),
    WITHDRAW_RULE(3, "提现规则"),
    MEMBER_RULE(4, "会员规则"),
    FANS_RULE(5, "粉丝规则"),
    INVITATION_RULE(6, "邀请规则"),
    ZERO_PURCHASE_RULE(7, "0元购规则"),
    NEW_USER_FREE_RULE(8, "新人免单规则");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    RuleType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static RuleType fromCode(Integer code){
        if(code == null) return null;
        for (RuleType e : RuleType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
