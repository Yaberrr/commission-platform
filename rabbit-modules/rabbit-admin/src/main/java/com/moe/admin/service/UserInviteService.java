package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.UserInviteDTO;
import com.moe.admin.domain.vo.user.InviteUserRankVO;
import com.moe.admin.domain.vo.user.InviteUserVO;

import java.util.List;

public interface UserInviteService {

    Page<InviteUserRankVO> selectInviteUserRankVOByDTO(IPage page, UserInviteDTO userInviteDTO);

    List<InviteUserVO> selectInviteUserVOByPeriod(UserInviteDTO userInviteDTO);
}
