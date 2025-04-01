package com.moe.common.core.enums.wallet;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 钱包记录状态
 */
@Getter
public enum WalletRecordStatus {

    NOT_RECEIVED(1, "未到账"),
    INVALID(2, "已失效"),
    RECEIVED(3, "已到账");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    WalletRecordStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static WalletRecordStatus fromCode(Integer code){
        if(code == null) return null;
        for (WalletRecordStatus e : WalletRecordStatus.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
