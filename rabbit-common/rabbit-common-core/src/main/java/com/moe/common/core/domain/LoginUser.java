package com.moe.common.core.domain;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.domain.sys.SysUser;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.SystemType;
import com.moe.common.core.enums.config.UserConfigType;
import com.moe.common.core.enums.platform.PlatformType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * app平台授权信息
     */
    private List<PlatformAuth> appAuthList;

    /**
     * 系统类型
     */
    private SystemType systemType;

    public PlatformAuth getPlatformAuth(PlatformType platformType){
        if(CollUtil.isEmpty(appAuthList)){
            return null;
        }
        return appAuthList.stream().filter(p -> platformType.equals(p.getPlatformType())).findFirst().orElse(null);
    }

}

