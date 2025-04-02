package com.moe.job.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Data;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author tangyabo
 * @date 2025/4/2
 */
@Configuration
@EnableCaching
public class CaffeineCacheConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(2000)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .expireAfterWrite(5,TimeUnit.MINUTES)
                .weakKeys()
                .recordStats());
        return cacheManager;
    }
}
