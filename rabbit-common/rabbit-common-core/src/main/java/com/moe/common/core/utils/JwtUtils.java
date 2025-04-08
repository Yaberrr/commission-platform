package com.moe.common.core.utils;

import java.util.Map;

import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.constant.SecurityConstants;
import com.moe.common.core.constant.TokenConstants;
import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.enums.SystemType;
import com.moe.common.core.text.Convert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Jwt工具类
 *
 * @author ruoyi
 */
public class JwtUtils
{
    public static String secret = TokenConstants.SECRET;

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createAccessToken(Map<String, Object> claims)
    {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param accessToken 令牌
     * @return 数据声明
     */
    public static Claims parseAccessToken(String accessToken)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(accessToken).getBody();
    }

   /* *//**
     * 根据令牌获取用户标识
     *
     * @param accessToken 令牌
     * @return 用户ID
     *//*
    public static String getUserKey(String accessToken)
    {
        Claims claims = parseAccessToken(accessToken);
        return getValue(claims, SecurityConstants.USER_KEY);
    }
*/
    /**
     * 根据令牌获取用户标识
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserKey(Claims claims)
    {
        return getValue(claims, SecurityConstants.USER_KEY);
    }

    /**
     * 根据令牌获取token key
     */
    public static String getTokenKey(String accessToken){
        Claims claims = parseAccessToken(accessToken);
        return JwtUtils.getTokenKey(claims);
    }
    public static String getTokenKey(Claims claims){
        String token = getValue(claims, SecurityConstants.USER_KEY);
        SystemType systemType = SystemType.valueOf(getValue(claims,SecurityConstants.SYSTEM_TYPE));
        return getTokenKey(token,systemType);
    }
    public static String getTokenKey(String token, SystemType systemType) {
        return systemType.getRedisPrefix() + CacheConstants.LOGIN_TOKEN_KEY  + token;
    }

    /**
     * 根据令牌获取userInfo key
     */
    public static String getUserInfoKey(String accessToken){
        Claims claims = parseAccessToken(accessToken);
        return JwtUtils.getUserInfoKey(claims);
    }
    public static String getUserInfoKey(Claims claims){
        String userId = getValue(claims, SecurityConstants.DETAILS_USER_ID);
        SystemType systemType = SystemType.valueOf(getValue(claims,SecurityConstants.SYSTEM_TYPE));
        return systemType.getRedisPrefix() + CacheConstants.USER_INFO_KEY  + userId;
    }
    public static String getUserInfoKey(LoginUser loginUser){
        Long userId;
        if(SystemType.ADMIN.equals(loginUser.getSystemType())){
            userId = loginUser.getSysUser().getUserId();
        }else{
            userId = loginUser.getAppUser().getId();
        }
        return loginUser.getSystemType().getRedisPrefix() + CacheConstants.USER_INFO_KEY + userId;
    }

    /**
     * 根据令牌获取用户ID
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserId(String token)
    {
        Claims claims = parseAccessToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }

    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserId(Claims claims)
    {
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }

    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUserName(String token)
    {
        Claims claims = parseAccessToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 根据身份信息获取用户名
     *
     * @param claims 身份信息
     * @return 用户名
     */
    public static String getUserName(Claims claims)
    {
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key 键
     * @return 值
     */
    public static String getValue(Claims claims, String key)
    {
        return Convert.toStr(claims.get(key), "");
    }
}
