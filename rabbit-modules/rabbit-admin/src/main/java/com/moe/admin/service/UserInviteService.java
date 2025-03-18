package com.moe.admin.service;

import com.moe.admin.domain.dto.user.UserInviteDTO;
import com.moe.admin.domain.vo.user.InviteUserRankVO;
import com.moe.admin.domain.vo.user.InviteUserVO;

import java.util.List;

public interface UserInviteService {

    List<InviteUserRankVO> selectInviteUserRankVOByDTO(UserInviteDTO userInviteDTO);

    List<InviteUserVO> selectInviteUserVOByPeriod(UserInviteDTO userInviteDTO);
}
