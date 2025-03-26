package com.moe.user.service;

import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.platform.vo.AuthUrlVO;

import java.util.List;

/**
 * 用户
 * @author tangyabo
 * @date 2025/3/12
 */
public interface UserService {

    /**
     * 保存用户
     * @param user
     * @return
     */
    User saveUser(User user);

}
