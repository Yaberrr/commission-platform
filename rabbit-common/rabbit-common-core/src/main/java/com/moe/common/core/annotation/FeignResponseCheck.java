package com.moe.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查Feign的返回结果
 */
@Target({ElementType.TYPE,ElementType.METHOD}) // 注解只能用于类
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时生效
public @interface FeignResponseCheck {

    String serviceName() default "";
}
