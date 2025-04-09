package com.moe.common.module.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.domain.config.PlatformConfig;
import com.moe.common.core.enums.config.PlatformConfigType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.Assert;
import com.moe.common.redis.service.RedisService;
import com.moe.common.module.mapper.PlatformConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 平台配置
 * @author tangyabo
 * @date 2025/4/1
 */
@Component
public class PlatformConfigService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private PlatformConfigMapper platformConfigMapper;

    /**
     * 获取平台配置
     * @param platformType
     * @param configType
     * @param
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfig(PlatformType platformType, PlatformConfigType configType) {
        String key = CacheConstants.PLATFORM_CONFIG_KEY+configType.name();
        String json = redisService.getCacheMapValue(key, platformType.name());
        if(StrUtil.isBlank(json)){
            //刷新缓存
            json = platformConfigMapper.selectOne(new LambdaQueryWrapper<PlatformConfig>()
                    .eq(PlatformConfig::getPlatformType, platformType)
                    .eq(PlatformConfig::getConfigType, configType)).getConfigJson();
            Assert.isTrue(StrUtil.isNotBlank(json),"平台配置不存在");
            redisService.setCacheMapValue(key,platformType.name(),json);
        }
        try {
            return  (T) new ObjectMapper().readValue(json, configType.getJsonClazz());
        } catch (JsonProcessingException e) {
            throw new ServiceException(e,"平台配置解析失败");
        }
    }

    /**
     *  更新平台配置
     * @param platformType
     * @param configType
     * @param configJson
     */
    public  void setConfig(PlatformType platformType, PlatformConfigType configType, String configJson) {
        String key = CacheConstants.PLATFORM_CONFIG_KEY+configType.name();
        if(StrUtil.isNotBlank(configJson)){
            redisService.setCacheMapValue(key, platformType.name(), configJson);
        }
    }
}
