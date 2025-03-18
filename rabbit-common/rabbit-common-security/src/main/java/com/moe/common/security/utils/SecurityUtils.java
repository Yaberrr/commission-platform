package com.moe.common.security.utils;

import javax.servlet.http.HttpServletRequest;

import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.sys.SysUser;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.exception.ServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.moe.common.core.constant.SecurityConstants;
import com.moe.common.core.constant.TokenConstants;
import com.moe.common.core.context.SecurityContextHolder;
import com.moe.common.core.utils.ServletUtils;
import com.moe.common.core.utils.StringUtils;

import java.util.Optional;

/**
 * 权限获取工具类
 *
 * @author ruoyi
 */
public class SecurityUtils
{

    /**
     * 获取登录用户信息
     */
    public static LoginUser getLoginUser()
    {
        return SecurityContextHolder.get(SecurityConstants.LOGIN_USER, LoginUser.class);

    }

    /**
     * 获取admin用户信息
     */
    public static SysUser getSysUser(){
        LoginUser loginUser = getLoginUser();
        return loginUser != null?loginUser.getSysUser():null;
    }

    /**
     * 获取app用户信息
     * @return
     */
    public static User getAppUser(){
        LoginUser loginUser = getLoginUser();
        return loginUser != null?loginUser.getAppUser():null;
    }


    /**
     * 从请求头中获取访问令牌
     */
    public static String getAccessToken()
    {
        return getAccessToken(ServletUtils.getRequest());
    }

    /**
     * 根据request获取访问令牌
     */
    public static String getAccessToken(HttpServletRequest request)
    {
        // 从header获取token标识
        String accessToken = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        return replaceTokenPrefix(accessToken);
    }

    /**
     * 裁剪token前缀
     */
    public static String replaceTokenPrefix(String token)
    {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX))
        {
            token = token.replaceFirst(TokenConstants.PREFIX, "");
        }
        return token;
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
