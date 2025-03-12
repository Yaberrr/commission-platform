package com.moe.common.core.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 会员等级
 */
@Getter
public enum MemberLevel {

    COPPER(1, "铜元宝"),
    SILVER(2, "银元宝"),
    GOLD(3, "金元宝");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    MemberLevel(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static MemberLevel fromCode(int code) {
        for (MemberLevel e : MemberLevel.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
