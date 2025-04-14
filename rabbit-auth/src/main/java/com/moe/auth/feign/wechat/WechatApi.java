package com.moe.auth.feign.wechat;

import com.moe.auth.feign.wechat.vo.WechatTokenVO;
import com.moe.auth.feign.wechat.vo.WechatUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tangyabo
 * @date 2025/4/9
 */
@FeignClient(name = "wechatApi",contextId = "wechatApi", url = "https://api.weixin.qq.com/sns", configuration = WechatFeignConfiguration.class)
public interface WechatApi {

    /**
     * 获取accessToken
     */
    @GetMapping(value = "/oauth2/access_token",produces = MediaType.APPLICATION_JSON_VALUE )
    WechatTokenVO getAccessToken(@RequestParam(name = "appid") String appid,
                                 @RequestParam(name = "secret") String secret,
                                 @RequestParam(name = "code") String code,
                                 @RequestParam(name = "grant_type") String grantType);

    /**
     * 获取用户信息
     */
    @GetMapping(value = "/userinfo",produces = MediaType.APPLICATION_JSON_VALUE)
    WechatUserVO getUserInfo(@RequestParam(name = "access_token") String accessToken,
                             @RequestParam(name = "openid") String openid);

}
