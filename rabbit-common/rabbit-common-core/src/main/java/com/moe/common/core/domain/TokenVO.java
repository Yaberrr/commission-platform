package com.moe.common.core.domain;

import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/3/14
 */
@Data
public class TokenVO {

    //token
    private String accessToken;

    //多少秒后到期
    private Long expiresIn;


    private String access_token;

    private Long expires_in;
}
