package com.moe.platform.service;

import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author tangyabo
 * @date 2025/3/19
 */
@Component
public class PlatformServiceFactory {

    private final Map<PlatformType, PlatformProductService> productServiceMap;
    private final Map<PlatformType, PlatformAuthService> authServiceMap;

    @Autowired
    public PlatformServiceFactory(Map<String, PlatformProductService> productMap,
                                  Map<String, PlatformAuthService> authMap) {
        this.productServiceMap = initializeMap(productMap, PlatformProductService.class);
        this.authServiceMap = initializeMap(authMap, PlatformAuthService.class);
    }

    private <T> Map<PlatformType, T> initializeMap(Map<String, T> serviceMap, Class<T> serviceType) {
        Map<PlatformType, T> resultMap = new HashMap<>();
        serviceMap.forEach((name, service) -> {
            try {
                // 匹配枚举名称
                PlatformType taskType = PlatformType.valueOf(name.replace(
                        (serviceType.getSimpleName().replace("Platform","")), "")
                        .toUpperCase());
                resultMap.put(taskType, service);
            } catch (IllegalArgumentException e) {
                throw new ServiceException("找不到service对应的枚举: " + name, e);
            }
        });
        return resultMap;
    }

    public PlatformProductService getProductService(PlatformType platformType) {
        return Optional.ofNullable(productServiceMap.get(platformType))
                .orElseThrow(() -> new ServiceException("未找到对应平台的商品服务: " + platformType.getDesc()));
    }

    public PlatformAuthService getAuthService(PlatformType platformType) {
        return Optional.ofNullable(authServiceMap.get(platformType))
                .orElseThrow(() -> new ServiceException("未找到对应平台的认证服务: " + platformType.getDesc()));
    }
}
