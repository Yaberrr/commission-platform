package com.moe.user.service;

import com.moe.common.core.domain.user.User;
import com.moe.user.domain.dto.UserConfigDTO;
import com.moe.user.domain.dto.UserDTO;
import com.moe.user.vo.UserMemberVO;

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
    Map<String, UserMemberVO> getUserMemberMap();

    /**
     * 修改用户信息
     * @param dto
     * @return
     */
    void updateInfo(UserDTO dto);

    /**
     * 修改用户配置
     * @param dto
     */
    void updateConfig(UserConfigDTO dto);
}
