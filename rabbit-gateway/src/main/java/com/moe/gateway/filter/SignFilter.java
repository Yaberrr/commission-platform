package com.moe.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moe.common.core.constant.HttpStatus;
import com.moe.common.core.constant.SecurityConstants;
import com.moe.common.core.utils.RSAUtils;
import com.moe.common.core.utils.ServletUtils;
import com.moe.common.core.utils.StringUtils;
import com.moe.common.redis.service.RedisService;
import com.moe.gateway.config.properties.IgnoreWhiteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

import static com.moe.common.core.constant.CacheConstants.INTERFACE_RANDOM_KEY;

/**
 * 密文过滤器
 * @author tangyabo
 * @date 2025/3/14
 */
@Component
public class SignFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(SignFilter.class);

    @Autowired
    private RedisService redisService;
    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    private static final Long TIME_OUT = 30L;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 跳过不需要验证的路径
        String url = exchange.getRequest().getURI().getPath();
        if (StringUtils.matches(url, ignoreWhite.getSigns())) {
            return chain.filter(exchange);
        }
        try {
            String encryptStr = exchange.getRequest().getHeaders().getFirst(SecurityConstants.ENCRYPT_STR);
            if(StringUtils.isBlank(encryptStr)){
                return forbiddenResponse(exchange, "密文为空");
            }
            if(!checkSign(encryptStr)){
                return forbiddenResponse(exchange, "密文有误");
            }
            return chain.filter(exchange);
        }catch (Exception e){
            return forbiddenResponse(exchange, "解析密文出现异常");
        }
    }

    /**
     * 校验密文 防重放攻击
     * @param encryptStr  密文
     * @return
     * @throws Exception
     */
    public boolean checkSign(String encryptStr){
        JSONObject validParam = JSON.parseObject(RSAUtils.rsaDecrypt(encryptStr, "rsa/private_key.pem"));
        Long timestamp = validParam.getLong("time");
        String randomStr = validParam.getString("randomStr");

        String redisKey = INTERFACE_RANDOM_KEY +timestamp + "_" + randomStr;

        //长时间通过时间戳拦截
        if (System.currentTimeMillis() - timestamp > TIME_OUT * 1000) {
            return false;
        }
        //短时间通过redis拦截
        if(redisService.hasKey(redisKey)){
            return false;
        }else{
            redisService.setCacheObject(redisKey,1, TIME_OUT, TimeUnit.SECONDS);
        }
        return true;
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
