package com.moe.common.core.enums.wallet;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 返佣级别
 */
@Getter
public enum WalletRewardLevel {

    SELF(1, "本人"),
    PARENT(2, "上游"),
    GRANDPARENT(3, "上上游");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    WalletRewardLevel(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static WalletRewardLevel fromCode(Integer code){
        if(code == null) return null;
        for (WalletRewardLevel e : WalletRewardLevel.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
