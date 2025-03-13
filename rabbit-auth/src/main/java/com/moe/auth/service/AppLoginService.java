package com.moe.auth.service;

import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.SystemType;
import com.moe.common.core.enums.user.Gender;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.Assert;
import com.moe.common.redis.service.RedisService;
import com.moe.message.api.RemoteSmsService;
import com.moe.message.body.SmsBody;
import com.moe.message.enums.SmsTemplate;
import com.moe.user.api.RemoteUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author tangyabo
 * @date 2025/3/11
 */
@Component
public class AppLoginService {

    //验证码有效时间
    private static final long CODE_TIMEOUT = 60L;
    private static final Logger log = LoggerFactory.getLogger(AppLoginService.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private RemoteSmsService remoteSmsService;
    @Autowired
    private RemoteUserService remoteUserService;


    public LoginUser mobileLogin(String phoneNumber, String code){
        String redisKey = CacheConstants.VERIFY_CODE_KEY + phoneNumber;
        String verifyCode = redisService.getCacheObject(redisKey);
        Assert.isTrue(verifyCode != null, "验证码已失效，请重新发送");
        Assert.isTrue(verifyCode.equals(code), "验证码有误");

        //保存用户
        User user = new User();
        user.setUserName(phoneNumber);
        user.setSex(Gender.UNKNOWN);
        user.setPhoneNumber(phoneNumber);
        R<User> r = remoteUserService.saveUser(user);
        r.check();

        //返回用户信息
        user = r.getData();
        LoginUser loginUser = new LoginUser();
        loginUser.setAppUser(user);
        loginUser.setUsername(user.getUserName());
        loginUser.setLoginTime(user.getLastLoginTime().getTime());
        loginUser.setSystemType(SystemType.APP);
        return loginUser;
    }

    /**
     * 发送手机验证码
     * @param phoneNumber
     */
    public R<Long> sendCode(String phoneNumber) {
        String redisKey = CacheConstants.VERIFY_CODE_KEY + phoneNumber;

        Long ttl = redisService.getExpire(redisKey, TimeUnit.SECONDS);
        if(ttl != null && ttl > 0){
            return R.fail(ttl,"验证码未到期");
        }

        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }

        Map<String,String> param = new HashMap<>();
        param.put("code",code.toString());
        SmsBody dto = new SmsBody();
        dto.setPhoneNumber(phoneNumber);
        dto.setTemplate(SmsTemplate.VERIFY_CODE);
        dto.setParam(param);
        R<?> r = remoteSmsService.sendOne(dto);
        r.check();

        redisService.setCacheObject(redisKey, code.toString(), CODE_TIMEOUT, TimeUnit.SECONDS);
        return R.ok(CODE_TIMEOUT,"验证码已发送");
    }
}
