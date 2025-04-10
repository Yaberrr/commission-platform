package com.moe.auth.body;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录对象
 *
 * @author ruoyi
 */
@Setter
@Getter
public class LoginBody
{
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

}
