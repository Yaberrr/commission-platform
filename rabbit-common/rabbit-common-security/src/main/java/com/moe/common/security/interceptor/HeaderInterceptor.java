package com.moe.common.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.enums.SystemType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import com.moe.common.core.constant.SecurityConstants;
import com.moe.common.core.context.SecurityContextHolder;
import com.moe.common.core.utils.ServletUtils;
import com.moe.common.core.utils.StringUtils;
import com.moe.common.security.auth.AuthUtil;
import com.moe.common.security.utils.SecurityUtils;


/**
 * 自定义请求头拦截器，将用户数据封装到线程变量中方便获取
 * 注意：此拦截器会同时验证当前用户有效期自动刷新有效期
 *
 * @author ruoyi
 */
public class HeaderInterceptor implements AsyncHandlerInterceptor
{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }
        //外部访问-通过网关传递 或 内部调用-Feign拦截器传递
         String accessToken = SecurityUtils.getAccessToken(request);

        if (StringUtils.isNotEmpty(accessToken))
        {
            LoginUser loginUser = AuthUtil.getLoginUser(accessToken);
            if (loginUser != null) {
                AuthUtil.verifyLoginUserExpire(loginUser);
                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception
    {
        SecurityContextHolder.remove();
    }
}
