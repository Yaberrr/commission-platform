package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.UserDTO;
import com.moe.admin.domain.dto.user.UserInviteDTO;
import com.moe.admin.domain.vo.user.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    Page<UserVO> selectUserVOByUserDTO(IPage page, @Param("dto") UserDTO userDTO);

    List<InviteUserVO> selectInviteUserVOByDTO(@Param("dto") UserDTO userDTO);

    List<InviteUserVO> selectGrandInviteUserVOByDTO(@Param("dto") UserDTO userDTO);

    UserDetailVO selectUserDetailByUserId(@Param("id") Long id);

    Page<InviteUserRankVO> selectInviteUserRankVOByDTO(IPage page, @Param("dto") UserInviteDTO userInviteDTO);

    List<InviteUserVO> selectInviteUserVOByPeriod(@Param("dto") UserInviteDTO userInviteDTO);

    List<UserListVO> selectUserListVO();
}
