package com.moe.user.service;

import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.User;

import java.util.Map;

/**
 * 用户
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
     * 获取用户等级map
     * @return
     */
    Map<String, Integer> userMemberLevelMap();
}
