package com.moe.auth.feign.wechat.vo;

import lombok.Data;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/9
 */
@Data
public class WechatUserVO extends WechatBaseVO{

    // 普通用户的标识，对当前开发者账号唯一
    private String openid;

    // 普通用户昵称
    private String nickname;

    // 普通用户性别，1 为男性，2 为女性
    private Integer sex;

    // 省份
    private String province;

    // 城市
    private String city;

    // 国家，如中国为 CN
    private String country;

    // 用户头像 URL
    private String headimgurl;

    // 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
    private List<String> privilege;

    // 用户统一标识。针对一个微信开放平台账号下的应用，同一用户的 unionid 是唯一的
    private String unionid;

}
