package com.moe.common.core.constant;

/**
 * 缓存常量信息
 *
 * @author ruoyi
 */
public class CacheConstants
{
    /**
     * 缓存有效期 30天
     */
    public final static long EXPIRATION = 43200;

    /**
     * 缓存刷新时间 29天
     */
    public final static long REFRESH_TIME = 41760;

    /**
     * 密码最大错误次数
     */
    public final static int PASSWORD_MAX_RETRY_COUNT = 5;

    /**
     * 密码锁定时间，默认10（分钟）
     */
    public final static long PASSWORD_LOCK_TIME = 10;

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 登录IP黑名单 cache key
     */
    public static final String SYS_LOGIN_BLACKIPLIST = SYS_CONFIG_KEY + "sys.login.blackIPList";

    /**
     * 验证码 cache key
     */
    public static final String VERIFY_CODE_KEY = "verify_code:";

    /**
     * 防重放攻击 cache key
     */
    public static final String INTERFACE_RANDOM_KEY = "interface_random:";

    /**
     * 平台配置
     */
    public static final String PLATFORM_CONFIG_KEY = "platform_config:";

    /**
     * 用户会员map
     */
    public static final String USER_MEMBER_MAP_KEY = "user_member_map";

    /**
     * 平台授权map
     */
    public static final String PLATFORM_AUTH_MAP_KEY = "platform_auth_map";

}
