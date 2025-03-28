package com.moe.common.security.feign;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.moe.common.core.constant.SecurityConstants;
import com.moe.common.core.utils.ServletUtils;
import com.moe.common.core.utils.StringUtils;
import com.moe.common.core.utils.ip.IpUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * feign 请求拦截器
 *
 * @author ruoyi
 */
public class FeignRequestInterceptor implements RequestInterceptor
{
    @Override
    public void apply(RequestTemplate requestTemplate)
    {
        //内部调用标识
        requestTemplate.header(SecurityConstants.FROM_SOURCE, SecurityConstants.INNER);
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (StringUtils.isNotNull(httpServletRequest))
        {
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
            //传递accessToken，防止丢失
            String authentication = headers.get(SecurityConstants.AUTHORIZATION_HEADER);
            if (StringUtils.isNotEmpty(authentication)) {
                requestTemplate.header(SecurityConstants.AUTHORIZATION_HEADER, authentication);
            }
            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr());
        }
    }
}
