package com.moe.common.core.enums.wallet;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
@Getter
public enum WalletEventType {

    ORDER_COMMISSION(1,"下单返佣", WalletFlowType.INCOME),
    WITHDRAW(2, "提现", WalletFlowType.EXPENSE);

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    private final WalletFlowType flowType;

    WalletEventType(int code, String desc, WalletFlowType flowType) {
        this.code = code;
        this.desc = desc;
        this.flowType = flowType;
    }

    @JsonCreator
    public static WalletEventType fromCode(Integer code){
        if(code == null) return null;
        for (WalletEventType e : WalletEventType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
