package com.moe.common.security.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * 内部调用Feign配置类
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

  /*  @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }*/
}
