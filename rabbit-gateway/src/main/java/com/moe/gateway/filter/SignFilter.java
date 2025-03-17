package com.moe.gateway.filter;

import com.moe.common.core.constant.HttpStatus;
import com.moe.common.core.utils.ServletUtils;
import com.moe.common.core.utils.StringUtils;
import com.moe.gateway.config.properties.IgnoreWhiteProperties;
import com.moe.gateway.utils.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 密文过滤器
 * @author tangyabo
 * @date 2025/3/14
 */
@Component
public class SignFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(SignFilter.class);

    @Autowired
    private SignUtils signUtils;

    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 跳过不需要验证的路径
        String url = exchange.getRequest().getURI().getPath();
        if (StringUtils.matches(url, ignoreWhite.getSigns())) {
            return chain.filter(exchange);
        }
        return exchange.getFormData().flatMap(
                formData -> {
                     try {
                         String encryptStr = formData.getFirst("encryptStr");
                         if(StringUtils.isEmpty(encryptStr) || !signUtils.checkSign(encryptStr)){
                            return forbiddenResponse(exchange, "密文有误");
                         }
                    }catch (Exception e){
                        return forbiddenResponse(exchange, "校验密文出错");
                    }
                    return chain.filter(exchange);
                }
        );
    }

    @Override
    public int getOrder() {
        return -201;
    };

    private Mono<Void> forbiddenResponse(ServerWebExchange exchange, String msg)
    {
        log.error("[密文异常处理]请求路径:{},错误信息:{}", exchange.getRequest().getPath(), msg);
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.FORBIDDEN);
    }

}
