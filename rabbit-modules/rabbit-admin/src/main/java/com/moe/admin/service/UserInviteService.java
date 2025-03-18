package com.moe.admin.service;

import com.moe.common.core.domain.dto.user.UserInviteDTO;
import com.moe.common.core.domain.vo.user.InviteUserRankVO;
import com.moe.common.core.domain.vo.user.InviteUserVO;

import java.util.List;

public interface UserInviteService {

    List<InviteUserRankVO> selectInviteUserRankVOByDTO(UserInviteDTO userInviteDTO);

    List<InviteUserVO> selectInviteUserVOByPeriod(UserInviteDTO userInviteDTO);
}
