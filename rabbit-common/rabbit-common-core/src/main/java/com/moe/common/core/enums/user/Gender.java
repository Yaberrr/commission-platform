package com.moe.common.core.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 性别
 */
@Getter
public enum Gender {

    MALE(0, "男"),
    FEMALE(1, "女"),
    UNKNOWN(2, "未知");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    Gender(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static Gender fromCode(Integer code){
        if(code == null) return null;
        for (Gender e : Gender.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
