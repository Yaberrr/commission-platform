package com.moe.admin.service;

import com.moe.admin.domain.dto.user.UserDTO;
import com.moe.admin.domain.vo.user.InviteUserVO;
import com.moe.admin.domain.vo.user.UserDetailVO;
import com.moe.admin.domain.vo.user.UserVO;

import java.util.List;

public interface UserService {

    List<UserVO> selectUserVOByDTO(UserDTO userDTO);

    List<InviteUserVO> selectInviteUserVOByDTO(UserDTO userDTO);

    List<InviteUserVO> selectGrandInviteUserVOByDTO(UserDTO userDTO);

    UserDetailVO selectUserDetailByUserId(Long id);

}
