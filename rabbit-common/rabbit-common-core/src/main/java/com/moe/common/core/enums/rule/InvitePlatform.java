package com.moe.common.core.enums.rule;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum InvitePlatform {
    wechat(1,"微信好友"),
    Moments(2,"朋友圈"),
    Share(3,"分享链接"),
    QRCode(4,"二维码");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    InvitePlatform(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    @JsonCreator
    public static InvitePlatform fromCode(int code) {
        for (InvitePlatform e : InvitePlatform.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
