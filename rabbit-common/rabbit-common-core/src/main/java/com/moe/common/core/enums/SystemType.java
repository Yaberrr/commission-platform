package com.moe.common.core.enums;

import com.moe.common.core.constant.ServiceNameConstants;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.moe.common.core.constant.ServiceNameConstants.*;


/**
 * 系统类型
 */
@Getter
public enum SystemType {

    ADMIN("admin_", Arrays.asList(ADMIN_SERVICE)),
    APP("app_", Arrays.asList(USER_SERVICE,PRODUCT_SERVICE,ORDER_SERVICE,WALLET_SERVICE));

    //redis key前缀
    private final String redisPrefix;

    //对外可访问的服务
    private final List<String> serviceIds;

    SystemType(String redisPrefix, List<String> serviceIds) {
        this.redisPrefix = redisPrefix;
        this.serviceIds = serviceIds;
    }
}
