package com.moe.common.core.domain;

import java.io.Serializable;
import java.util.Set;
import com.moe.common.core.domain.sys.SysUser;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.SystemType;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@Setter
@Getter
public class LoginUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * admin用户信息
     */
    private SysUser sysUser;

    /**
     * app用户信息
     */
    private User appUser;

    /**
     * 系统类型
     */
    private SystemType systemType;

}

