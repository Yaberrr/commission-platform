package com.moe.auth.service;

import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.domain.R;
import com.moe.common.core.utils.Assert;
import com.moe.common.redis.service.RedisService;
import com.moe.message.api.RemoteSmsService;
import com.moe.message.body.SmsBody;
import com.moe.message.enums.SmsTemplate;
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

    @Autowired
    private RedisService redisService;
    @Autowired
    private RemoteSmsService remoteSmsService;

    public void mobileLogin(String phoneNumber, String code){


    }

    /**
     * 发送手机验证码
     * @param phoneNumber
     */
    public void sendCode(String phoneNumber) {
        String redisKey = CacheConstants.VERIFY_CODE_KEY + phoneNumber;

        Long ttl = redisService.getExpire(redisKey, TimeUnit.SECONDS);
        Assert.isFalse(ttl!=null && ttl>0,"验证码剩余时间: " + ttl + " 秒");

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
        redisService.setCacheObject(redisKey, code.toString(), CODE_TIMEOUT, TimeUnit.SECONDS);
    }
}
