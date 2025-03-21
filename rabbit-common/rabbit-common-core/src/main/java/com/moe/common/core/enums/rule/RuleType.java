package com.moe.common.core.enums.rule;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum RuleType {

    Order(1,"订单"),
    Profit(2,"收益"),
    Withdrawal(3,"提现"),
    Member(4,"会员"),
    Fans(5,"粉丝"),
    Invite(6,"邀请"),
    FreePurchase(7,"0元购"),
    NewFreeOrder(8,"新人免单");
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
