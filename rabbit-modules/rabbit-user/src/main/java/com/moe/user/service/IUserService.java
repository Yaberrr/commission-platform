package com.moe.user.service;

import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.platform.vo.AuthUrlVO;

/**
 * @author tangyabo
 * @date 2025/3/12
 */
public interface IUserService {

    /**
     * 保存用户
     * @param user
     * @return
     */
    User saveUser(User user);

    /**
     * 获取平台授权url
     * @param platformType 平台类型
     * @return
     */
    AuthUrlVO getPlatformAuthUrl(PlatformType platformType);

}
