package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.vo.user.UserListVO;
import com.moe.admin.mapper.UserMapper;
import com.moe.admin.service.UserService;
import com.moe.admin.domain.dto.user.UserDTO;
import com.moe.admin.domain.vo.user.InviteUserVO;
import com.moe.admin.domain.vo.user.UserDetailVO;
import com.moe.admin.domain.vo.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<UserVO> selectUserVOByDTO(IPage page, UserDTO userDTO) {
        return userMapper.selectUserVOByUserDTO(page, userDTO);
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

    @Override
    public List<UserListVO> selectUserListVO(UserDTO userDTO) {
        return userMapper.selectUserListVO(userDTO);
    }
}
