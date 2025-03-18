package com.moe.common.security.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * Feign 全局配置
 *
 * @author ruoyi
 **/
public class GlobalFeignConfiguration
{
    @Bean
    public RequestInterceptor requestInterceptor()
    {
        return new FeignRequestInterceptor();
    }

}
