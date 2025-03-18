package com.moe.auth.feign.aurora;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moe.auth.feign.aurora.vo.AuroraLoginVo;
import com.moe.common.core.exception.ServiceException;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * 极光API配置
 * @author tangyabo
 * @date 2025/3/17
 */
@Configuration
public class AuroraFeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor()
    {
        return new AuroraRequestInterceptor();
    }

   /* @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }*/

    /*@Bean
    public ErrorDecoder feignErrorDecoder() {
        return (response, type) -> {
            // 打印请求体
            try {
                InputStream inputStream = type.body().asInputStream();
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                if ("gzip".equalsIgnoreCase(type.headers().get("Content-Encoding").stream().findFirst().orElse(""))) {
                    GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = gzipInputStream.read(buffer)) != -1) {
                        result.write(buffer, 0, length);
                    }
                    gzipInputStream.close();
                } else {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) != -1) {
                        result.write(buffer, 0, length);
                    }
                }
                //将二进制数据转换为字符串
                String requestBody = result.toString("UTF-8");
                AuroraLoginVo vo = new ObjectMapper().readValue(requestBody, AuroraLoginVo.class);
                throw new ServiceException("请求异常:" + vo.getContent());
            } catch (IOException e) {
                throw new ServiceException("Failed to read request body: " + e.getMessage());
            }
        };
    }*/
}

