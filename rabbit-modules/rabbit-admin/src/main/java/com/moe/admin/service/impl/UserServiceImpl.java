package com.moe.admin.service.impl;

import com.moe.admin.mapper.UserMapper;
import com.moe.admin.service.UserService;
import com.moe.common.core.domain.dto.user.UserDTO;
import com.moe.common.core.domain.vo.user.InviteUserVO;
import com.moe.common.core.domain.vo.user.UserDetailVO;
import com.moe.common.core.domain.vo.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserVO> selectUserVOByDTO(UserDTO userDTO) {
        return userMapper.selectUserVOByUserDTO(userDTO);
    }

    @Override
    public List<InviteUserVO> selectInviteUserVOByDTO(UserDTO userDTO) {
        return userMapper.selectInviteUserVOByDTO(userDTO);
    }

    @Override
    public List<InviteUserVO> selectGrandInviteUserVOByDTO(UserDTO userDTO) {
        return userMapper.selectGrandInviteUserVOByDTO(userDTO);
    }

    @Override
    public UserDetailVO selectUserDetailByUserId(Long id) {
        return userMapper.selectUserDetailByUserId(id);
    }
}
