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
public class ProductServiceFactory {

    private final Map<PlatformType, ProductService> productServiceMap = new HashMap<>();

    @Autowired
    public ProductServiceFactory(Map<String, ProductService> serviceMap) {
        serviceMap.forEach((name, service) -> {
            try {
                //匹配枚举名称
                PlatformType taskType = PlatformType.valueOf(name.replace("ProductService", "").toUpperCase());
                productServiceMap.put(taskType, service);
            } catch (IllegalArgumentException e) {
                throw new ServiceException("找不到service对应的枚举: " + name, e);
            }
        });
    }

    public ProductService getProductService(PlatformType platformType) {
        return Optional.ofNullable(productServiceMap.get(platformType))
                .orElseThrow(() -> new ServiceException("未找到对应的商品服务: " + platformType.getDesc()));
    }
}
