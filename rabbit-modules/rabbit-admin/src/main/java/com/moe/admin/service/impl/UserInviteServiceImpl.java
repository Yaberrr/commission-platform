package com.moe.admin.service.impl;

import com.moe.admin.mapper.UserMapper;
import com.moe.admin.service.UserInviteService;
import com.moe.common.core.domain.dto.user.UserInviteDTO;
import com.moe.common.core.domain.vo.user.InviteUserRankVO;
import com.moe.common.core.domain.vo.user.InviteUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInviteServiceImpl implements UserInviteService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<InviteUserRankVO> selectInviteUserRankVOByDTO(UserInviteDTO userInviteDTO) {
        return userMapper.selectInviteUserRankVOByDTO(userInviteDTO);
    }

    @Override
    public List<InviteUserVO> selectInviteUserVOByPeriod(UserInviteDTO userInviteDTO) {
        return userMapper.selectInviteUserVOByPeriod(userInviteDTO);
    }
}
