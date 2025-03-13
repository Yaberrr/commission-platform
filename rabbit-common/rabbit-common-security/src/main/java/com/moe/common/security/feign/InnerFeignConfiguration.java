package com.moe.common.security.feign;

import feign.Feign;
import feign.Request;
import org.springframework.context.annotation.Bean;
import feign.RequestInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * Feign 配置注册
 *
 * @author ruoyi
 **/
public class InnerFeignConfiguration
{
    @Bean
    public RequestInterceptor requestInterceptor()
    {
        return new FeignRequestInterceptor();
    }

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .options(new Request.Options(
                        5000, TimeUnit.MILLISECONDS,
                        10000, TimeUnit.MILLISECONDS,
                        true));
    }
}
