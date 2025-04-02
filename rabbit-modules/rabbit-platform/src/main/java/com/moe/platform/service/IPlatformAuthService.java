package com.moe.platform.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.exception.ServiceException;
import com.moe.platform.vo.PlatformUrlVO;

import java.util.Collections;
import java.util.Map;

/**
 * 平台授权
 * @author tangyabo
 * @date 2025/3/24
 */
public interface IPlatformAuthService {

    /**
     * 生成授权信息
     * @return
     */
    PlatformAuth createAuth(Long userId);

    /**
     * 检查授权状态
     * @return 是否授权
     */
    boolean checkAuth(PlatformAuth auth);

    /**
     * 生成授权链接
     * @return
     */
    default PlatformUrlVO generateAuthUrl(PlatformAuth auth){
        throw new ServiceException("当前平台不支持生成授权链接");
    }

}
