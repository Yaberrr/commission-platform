package com.moe.gateway.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moe.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.moe.common.core.constant.CacheConstants.INTERFACE_RANDOM_KEY;

/**
 * 密文工具类
 * @author tangyabo
 * @date 2025/3/14
 */
@Component
public class SignUtils {

    private static final Long TIME_OUT = 30L;

    @Autowired
    private RedisService redisService;


    /**
     * 校验密文 防重放攻击
     * @param encryptStr  密文
     * @return
     * @throws Exception
     */
    public boolean checkSign(String encryptStr) throws Exception {
        JSONObject validParam = JSON.parseObject(RSAUtils.rsaDecrypt(encryptStr));
        Long timestamp = validParam.getLong("time");
        String randomStr = validParam.getString("randomStr");

        String redisKey = INTERFACE_RANDOM_KEY +timestamp + "_" + randomStr;

        //长时间通过时间戳拦截
        if (System.currentTimeMillis() - timestamp > TIME_OUT * 1000) {
            return false;
        }

        //短时间通过redis拦截
        if(redisService.hasKey(redisKey)){
            return false;
        }else{
            redisService.setCacheObject(redisKey,1, TIME_OUT, TimeUnit.SECONDS);
        }

        return true;
    }

}
