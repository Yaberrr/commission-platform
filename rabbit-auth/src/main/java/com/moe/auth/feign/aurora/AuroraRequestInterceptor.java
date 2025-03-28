package com.moe.auth.feign.aurora;

import cn.hutool.core.codec.Base64;
import com.moe.common.core.constant.SecurityConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;

/**
 * 极光API拦截器
 * @author tangyabo
 * @date 2025/3/17
 */
public class AuroraRequestInterceptor implements RequestInterceptor {

    @Value("${aurora.appKey}")
    private String appKey;
    @Value("${aurora.masterSecret}")
    private String masterSecret;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(SecurityConstants.AUTHORIZATION_HEADER,
                "Basic " + Base64.encode(appKey + ":" + masterSecret, StandardCharsets.UTF_8));
    }
}
