package com.moe.product.service;

import com.moe.platform.dto.PlatformParam;

import java.util.List;

/**
 * 商品组
 * @author tangyabo
 * @date 2025/3/21
 */
public interface ProductGroupService {

    /**
     * 查询平台参数
     * @param groupId 商品组id
     * @return
     */
    List<PlatformParam> platformParamList(Long groupId);

}
