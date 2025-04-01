package com.moe.job.service;

import com.moe.common.core.enums.platform.PlatformType;

/**
 * 订单定时服务
 * @author tangyabo
 * @date 2025/3/31
 */
public interface IOrderJobService {

    /**
     * 更新订单
     * @param platformType 平台类型
     * @param seconds 最近多少秒
     */
    void updateOrder(Integer platformType, long seconds);

}
