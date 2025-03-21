package com.moe.common.core.enums.config;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 用户配置类型
 */
@Getter
public enum UserConfigType {

    SHARE_WITH_APP_DOWNLOAD(1, "商品分享带APP下载"),
    HIDE_ORDER_INFO(2, "隐藏我的订单信息");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    UserConfigType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static UserConfigType fromCode(Integer code){
        if(code == null) return null;
        for (UserConfigType e : UserConfigType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
