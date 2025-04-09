package com.moe.auth.feign.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author tangyabo
 * @date 2025/4/9
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {

    private String appId;

    private String appSecret;

}
