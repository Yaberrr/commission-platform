package com.moe.product.service;

import com.moe.common.core.domain.R;

import java.util.Map;

/**
 * @author tangyabo
 * @date 2025/4/14
 */
public interface IIndexDiyService {

    /**
     * 获取版本对应的首页装修
     * @param appVersion
     * @return
     */
    Map<String, Object> getIndexDiy(String appVersion);

}
