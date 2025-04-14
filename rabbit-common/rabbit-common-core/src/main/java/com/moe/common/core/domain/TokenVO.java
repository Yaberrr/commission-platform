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

    //手机号
    private String phoneNumber;

}
