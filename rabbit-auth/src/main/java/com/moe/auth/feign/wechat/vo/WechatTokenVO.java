package com.moe.auth.feign.wechat.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/4/9
 */
@Data
public class WechatTokenVO extends WechatBaseVO{

    //接口调用凭证
    @JsonProperty("access_token")
    private String accessToken;

    //接口调用凭证超时时间，单位（秒）
    @JsonProperty("expires_in")
    private Integer expiresIn;

    // 用户刷新token
    @JsonProperty("refresh_token")
    private String refreshToken;

    //授权用户唯一标识
    private String openid;

    //用户授权的作用域，使用逗号（,）分隔
    private String scope;

    //用户统一标识。针对一个微信开放平台账号下的应用，同一用户的 unionid 是唯一的
    private String unionid;

}
