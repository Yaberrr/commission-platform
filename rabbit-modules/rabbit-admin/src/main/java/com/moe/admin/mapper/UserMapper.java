package com.moe.admin.mapper;

import com.moe.common.core.domain.dto.user.UserDTO;
import com.moe.common.core.domain.dto.user.UserInviteDTO;
import com.moe.common.core.domain.vo.user.InviteUserRankVO;
import com.moe.common.core.domain.vo.user.InviteUserVO;
import com.moe.common.core.domain.vo.user.UserDetailVO;
import com.moe.common.core.domain.vo.user.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    List<UserVO> selectUserVOByUserDTO(@Param("dto") UserDTO userDTO);

    List<InviteUserVO> selectInviteUserVOByDTO(@Param("dto") UserDTO userDTO);

    List<InviteUserVO> selectGrandInviteUserVOByDTO(@Param("dto") UserDTO userDTO);

    UserDetailVO selectUserDetailByUserId(@Param("id") Long id);

    List<InviteUserRankVO> selectInviteUserRankVOByDTO(@Param("dto") UserInviteDTO userInviteDTO);

    List<InviteUserVO> selectInviteUserVOByPeriod(@Param("dto") UserInviteDTO userInviteDTO);
}
