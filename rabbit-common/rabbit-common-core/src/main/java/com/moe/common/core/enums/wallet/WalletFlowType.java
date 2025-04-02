package com.moe.common.core.enums.wallet;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 钱包流水类型
 */
@Getter
public enum WalletFlowType {

    INCOME(0, "收入"),
    EXPENSE(1, "支出");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    WalletFlowType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static WalletFlowType fromCode(Integer code){
        if(code == null) return null;
        for (WalletFlowType e : WalletFlowType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
