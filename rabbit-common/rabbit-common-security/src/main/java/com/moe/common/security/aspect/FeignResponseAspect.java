package com.moe.common.security.aspect;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.constant.HttpStatus;
import com.moe.common.core.domain.R;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.web.page.TableDataInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * Feign响应切面
 * @author tangyabo
 * @date 2025/3/21
 */
@Aspect
@Component
public class FeignResponseAspect {

    /**
     * 注解切入点
     */
    @Pointcut("@within(com.moe.common.core.annotation.FeignResponseCheck) || @annotation(com.moe.common.core.annotation.FeignResponseCheck)")
    public void feignResponseCheckPoint() {}

    /**
     * 拦截响应
     * @param result
     */
    @AfterReturning(pointcut = "feignResponseCheckPoint()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String msg = null;
        Integer code = HttpStatus.SUCCESS;
        Object errorData = null;
        if (result instanceof TableDataInfo) {
            TableDataInfo<?> info = (TableDataInfo<?>) result;
            msg = info.getMsg();
            code = info.getCode();
        }else if(result instanceof R){
            R<?> r = (R<?>) result;
            msg = r.getMsg();
            code = r.getCode();
            errorData = r.getErrorData();
        }
        if(code != HttpStatus.SUCCESS) {
            Class<?> targetClass = joinPoint.getTarget().getClass();
            FeignResponseCheck annotation = AnnotationUtils.findAnnotation(targetClass, FeignResponseCheck.class);
            if(annotation == null){
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                annotation = signature.getMethod().getAnnotation(FeignResponseCheck.class);
            }
            throw new ServiceException(code, errorData, annotation.serviceName() + "服务异常:{}", msg);
        }
    }

}
