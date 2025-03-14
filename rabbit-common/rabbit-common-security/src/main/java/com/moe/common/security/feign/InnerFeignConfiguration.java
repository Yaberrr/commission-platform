package com.moe.common.security.feign;

import org.springframework.context.annotation.Bean;
import feign.RequestInterceptor;

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

}
