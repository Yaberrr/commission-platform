package com.moe.auth.service;

import cn.hutool.core.util.StrUtil;
import com.moe.admin.api.FileApi;
import com.moe.auth.feign.aurora.AuroraApi;
import com.moe.auth.feign.aurora.body.AuroraLoginBody;
import com.moe.auth.feign.aurora.vo.AuroraLoginVo;
import com.moe.auth.feign.wechat.WechatApi;
import com.moe.auth.feign.wechat.WechatConfig;
import com.moe.auth.feign.wechat.vo.WechatTokenVO;
import com.moe.auth.feign.wechat.vo.WechatUserVO;
import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.SystemType;
import com.moe.common.core.enums.user.Gender;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.Assert;
import com.moe.common.core.utils.RSAUtils;
import com.moe.common.redis.service.RedisService;
import com.moe.message.api.ISmsApi;
import com.moe.message.dto.SmsDTO;
import com.moe.message.enums.SmsTemplate;
import com.moe.platform.api.IPlatformAuthApi;
import com.moe.user.api.IUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author tangyabo
 * @date 2025/3/11
 */
@Component
public class AppLoginService {

    //验证码有效时间  5分钟内有效
    private static final long CODE_TIMEOUT = 300L;
    //可重发时间  1分钟后可重发
    private static final long CODE_RETRY = 60L;
    //手机号正则
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d*$");

    @Autowired
    private RedisService redisService;
    @Autowired
    private ISmsApi smsApi;
    @Autowired
    private IUserApi userApi;
    @Autowired
    private IPlatformAuthApi platformAuthApi;
    @Autowired
    private AuroraApi auroraApi;
    @Autowired
    private WechatApi wechatApi;
    @Autowired
    private WechatConfig wechatConfig;
    @Autowired
    private FileApi fileApi;


    /**
     * 发送手机验证码
     * @param phoneNumber
     */
    public R<Long> sendCode(String phoneNumber) {
        Assert.isTrue(PHONE_PATTERN.matcher(phoneNumber).matches(),"手机号格式不正确");

        String redisKey = CacheConstants.VERIFY_CODE_KEY + phoneNumber;
        Long ttl = redisService.getExpire(redisKey, TimeUnit.SECONDS);
        //一分钟内不可重发
        if(ttl != null && CODE_TIMEOUT - ttl < CODE_RETRY){
            return R.fail(ttl,"请稍后发送");
        }

        StringBuilder code = new StringBuilder();
        if(ttl != null && ttl > 0){
            //旧的验证码
            code.append(redisService.getCacheObject(redisKey).toString());
        }else {
            //重新生成验证码
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                code.append(random.nextInt(10));
            }
        }

        Map<String,String> param = new HashMap<>();
        param.put("code",code.toString());
        SmsDTO dto = new SmsDTO();
        dto.setPhoneNumber(phoneNumber);
        dto.setTemplate(SmsTemplate.VERIFY_CODE);
        dto.setParam(param);
        smsApi.sendOne(dto);

        //刷新过期时间
        redisService.setCacheObject(redisKey, code.toString(), CODE_TIMEOUT, TimeUnit.SECONDS);
        return R.ok(CODE_TIMEOUT,"验证码已发送");
    }

    /**
     * 手机验证码登录
     * @param phoneNumber
     * @param code
     * @return
     */
    public LoginUser mobileLogin(String phoneNumber, String code){
        //todo: 测试用验证码
        if(!"123456".equals(code)){
            String redisKey = CacheConstants.VERIFY_CODE_KEY + phoneNumber;
            String verifyCode = redisService.getCacheObject(redisKey);
            Assert.isTrue(verifyCode != null, "验证码已失效，请重新发送");
            Assert.isTrue(verifyCode.equals(code), "验证码有误");
            Assert.isTrue(PHONE_PATTERN.matcher(phoneNumber).matches(),"手机号格式不正确");
        }

        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setUserName("元宝宝" + phoneNumber.substring(phoneNumber.length()-4));
        return this.saveUser(user);
    }

    /**
     * 手机一键登录
     * @param loginToken
     * @return
     */
    public LoginUser quickLogin(String loginToken) {
        AuroraLoginBody body = new AuroraLoginBody();
        body.setLoginToken(loginToken);
        AuroraLoginVo vo = auroraApi.loginTokenVerify(body);
        Assert.isTrue(vo.getCode() == 8000, "一键登陆服务异常，"+vo.getContent());
        String phoneNumber = RSAUtils.rsaDecrypt(vo.getPhone(), "rsa/aurora_private_key.pem");
        Assert.isTrue(PHONE_PATTERN.matcher(phoneNumber).matches(),"手机号格式不正确");
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setUserName("元宝宝" + phoneNumber.substring(phoneNumber.length()-4));
        return this.saveUser(user);
    }

    public LoginUser wechatLogin(String code) {
        WechatTokenVO tokenVO = wechatApi.getAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret(), code, "authorization_code");
        if(StrUtil.isNotBlank(tokenVO.getErrcode())){
            throw new ServiceException("微信授权失败:{}",tokenVO.getErrmsg());
        }
        WechatUserVO userVO = wechatApi.getUserInfo(tokenVO.getAccessToken(), tokenVO.getOpenid());
        if(StrUtil.isNotBlank(userVO.getErrcode())){
            throw new ServiceException("微信用户查询失败:{}",userVO.getErrmsg());
        }
        User user = new User();
        user.setWechatOpenId(userVO.getOpenid());
        user.setUserName(userVO.getNickname());
        //todo:头像保存到云服务器，微信的url会过期
        user.setAvatarUrl(userVO.getHeadimgurl());
        return this.saveUser(user);
    }

    private LoginUser saveUser(User user){
        //保存用户
        user.setSex(Gender.UNKNOWN);
        user = userApi.saveUser(user).getData();

        //查询授权信息
        List<PlatformAuth> authList = platformAuthApi.authList(user.getId()).getData();
        LoginUser loginUser = new LoginUser();
        loginUser.setAppUser(user);
        loginUser.setUsername(user.getUserName());
        loginUser.setLoginTime(user.getLastLoginTime().getTime());
        loginUser.setSystemType(SystemType.APP);
        loginUser.setAppAuthList(authList);
        return loginUser;
    }


}
