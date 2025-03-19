package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.UserDTO;
import com.moe.admin.domain.vo.user.InviteUserVO;
import com.moe.admin.domain.vo.user.UserDetailVO;
import com.moe.admin.domain.vo.user.UserListVO;
import com.moe.admin.domain.vo.user.UserVO;

import java.util.List;

public interface UserService {

    Page<UserVO> selectUserVOByDTO(IPage page, UserDTO userDTO);

    List<InviteUserVO> selectInviteUserVOByDTO(UserDTO userDTO);

    List<InviteUserVO> selectGrandInviteUserVOByDTO(UserDTO userDTO);

    UserDetailVO selectUserDetailByUserId(Long id);

    List<UserListVO> selectUserListVO();

}
