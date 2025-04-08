package com.moe.gateway.filter;

import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.enums.SystemType;
import com.moe.gateway.config.properties.IgnoreWhiteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.moe.common.core.constant.HttpStatus;
import com.moe.common.core.constant.SecurityConstants;
import com.moe.common.core.constant.TokenConstants;
import com.moe.common.core.utils.JwtUtils;
import com.moe.common.core.utils.ServletUtils;
import com.moe.common.core.utils.StringUtils;
import com.moe.common.redis.service.RedisService;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * 网关鉴权
 *
 * @author ruoyi
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered
{
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Autowired
    private RedisService redisService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();
        // 跳过不需要验证的路径
        if (StringUtils.matches(url, ignoreWhite.getWhites()))
        {
            return chain.filter(exchange);
        }
        String accessToken = getAccessToken(request);
        if (StringUtils.isEmpty(accessToken))
        {
            return unauthorizedResponse(exchange, "令牌不能为空");
        }
        Claims claims = JwtUtils.parseAccessToken(accessToken);
        if (claims == null)
        {
            return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
        }

        String tokenKey = JwtUtils.getTokenKey(claims);
        long expireTime = redisService.getExpire(tokenKey);
        if(expireTime < 0){
            return unauthorizedResponse(exchange, "登录状态已过期");
        }
        //自动刷新令牌
        if (expireTime - System.currentTimeMillis() <= CacheConstants.LOGIN_REFRESH_TIME*60*1000) {
            redisService.expire(tokenKey, CacheConstants.LOGIN_EXPIRE_TIME, TimeUnit.MINUTES);
            redisService.expire(JwtUtils.getUserInfoKey(claims), CacheConstants.LOGIN_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        //用户系统类型
        SystemType systemType = SystemType.valueOf(JwtUtils.getValue(claims, SecurityConstants.SYSTEM_TYPE));
        //校验用户可用服务
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        if(route == null){
            log.error("[查找服务异常]请求路径:{},错误信息:{}", exchange.getRequest().getPath(), "服务不存在");
            return ServletUtils.webFluxResponseWriter(exchange.getResponse(), "服务不存在", HttpStatus.NOT_FOUND);
        }
        String serviceId = route.getId();
        if(!StringUtils.matches(serviceId,systemType.getServiceIds())){
           return unauthorizedResponse(exchange, "令牌不可用于当前服务");
        }
        String userid = JwtUtils.getUserId(claims);
        String username = JwtUtils.getUserName(claims);
        if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username)) {
            return unauthorizedResponse(exchange, "令牌验证失败");
        }
        // 内部请求来源参数清除
        removeHeader(mutate, SecurityConstants.FROM_SOURCE);

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value)
    {
        if (value == null)
        {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    private void removeHeader(ServerHttpRequest.Builder mutate, String name)
    {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg)
    {
        log.error("[鉴权异常处理]请求路径:{},错误信息:{}", exchange.getRequest().getPath(), msg);
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }


    /**
     * 获取请求token
     */
    private String getAccessToken(ServerHttpRequest request)
    {
        String token = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION_HEADER);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX))
        {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    @Override
    public int getOrder()
    {
        return -200;
    }
}
