package com.moe.user.service;

import com.moe.common.core.domain.user.User;

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
