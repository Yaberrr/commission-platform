package com.moe.platform.config;

import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tangyabo
 * @date 2025/3/18
 */
@Configuration
public class PddConfig {

    @Value("pdd.clientId")
    private String clientId;

    @Value("pdd.clientSecret")
    private String clientSecret;

    @Bean
    public PopClient popClient() {
        return new PopHttpClient(clientId, clientSecret);
    }

}
