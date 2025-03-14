package com.moe.common.security.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.TokenVO;
import com.moe.common.core.domain.sys.SysUser;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.SystemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.constant.SecurityConstants;
import com.moe.common.core.utils.JwtUtils;
import com.moe.common.core.utils.ServletUtils;
import com.moe.common.core.utils.StringUtils;
import com.moe.common.core.utils.ip.IpUtils;
import com.moe.common.core.utils.uuid.IdUtils;
import com.moe.common.redis.service.RedisService;
import com.moe.common.security.utils.SecurityUtils;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Component
public class TokenService
{
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    private RedisService redisService;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long expireTime = CacheConstants.EXPIRATION;


    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;

    /**
     * 创建令牌
     */
    public TokenVO createToken(LoginUser loginUser)
    {
        String token = IdUtils.fastUUID();
        Long userId;
        String userName;
        if(loginUser.getSystemType().equals(SystemType.ADMIN)) {
            SysUser sysUser = loginUser.getSysUser();
            userId = sysUser.getUserId();
            userName = sysUser.getUserName();
        }else{
            User appUser = loginUser.getAppUser();
            userId = appUser.getId();
            userName = appUser.getUserName();
        }
        loginUser.setToken(token);
        loginUser.setUsername(userName);
        loginUser.setIpaddr(IpUtils.getIpAddr());
        this.refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);
        claimsMap.put(SecurityConstants.SYSTEM_TYPE, loginUser.getSystemType().name());

        // 接口返回信息
        TokenVO vo = new TokenVO();
        vo.setAccessToken(JwtUtils.createAccessToken(claimsMap));
        vo.setExpiresIn(expireTime);
        vo.setAccess_token(vo.getAccessToken());
        vo.setExpires_in(expireTime);
        return vo;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser()
    {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String accessToken = SecurityUtils.getAccessToken(request);
        return getLoginUser(accessToken);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(String accessToken)
    {
        LoginUser user = null;
        try
        {
            if (StringUtils.isNotEmpty(accessToken))
            {
                user = redisService.getCacheObject(JwtUtils.getRedisKey(accessToken));
                return user;
            }
        }
        catch (Exception e)
        {
            log.error("获取用户信息异常'{}'", e.getMessage());
        }
        return user;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser)
    {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String accessToken)
    {
        if (StringUtils.isNotEmpty(accessToken))
        {
            redisService.deleteObject(JwtUtils.getRedisKey(accessToken));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param loginUser
     */
    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String redisKey = JwtUtils.getRedisKey(loginUser.getToken(), loginUser.getSystemType());
        redisService.setCacheObject(redisKey, loginUser, expireTime, TimeUnit.MINUTES);
    }


}
