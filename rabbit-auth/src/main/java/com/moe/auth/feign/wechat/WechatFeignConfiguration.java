package com.moe.auth.feign.wechat;

import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/9
 */
public class WechatFeignConfiguration {

    @Bean
    public Decoder feignDecoder() {
        ObjectFactory<HttpMessageConverters> messageConverters = () -> {
            List<HttpMessageConverter<?>> converters = new ArrayList<>();
            // 添加能处理text/plain的JSON转换器
            converters.add(new MappingJackson2HttpMessageConverter() {
                @Override
                public boolean canRead(Class<?> clazz, MediaType mediaType) {
                    return super.canRead(clazz, mediaType) ||
                            (mediaType != null && mediaType.includes(MediaType.TEXT_PLAIN));
                }
            });
            return new HttpMessageConverters(converters);
        };
        return new SpringDecoder(messageConverters);
    }
}
