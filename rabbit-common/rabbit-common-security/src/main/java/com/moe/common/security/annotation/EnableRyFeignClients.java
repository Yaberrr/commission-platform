package com.moe.common.security.annotation;

import com.moe.common.security.feign.GlobalFeignConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import java.lang.annotation.*;

/**
 * 自定义feign注解
 * 添加basePackages路径
 *
 * @author ruoyi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableRyFeignClients
{
    String[] value() default {};

    String[] basePackages() default { "com.moe" };

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default { GlobalFeignConfiguration.class };

    Class<?>[] clients() default {};
}
