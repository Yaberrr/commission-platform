package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.mapper.UserMapper;
import com.moe.admin.service.UserInviteService;
import com.moe.admin.domain.dto.user.UserInviteDTO;
import com.moe.admin.domain.vo.user.InviteUserRankVO;
import com.moe.admin.domain.vo.user.InviteUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInviteServiceImpl implements UserInviteService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<InviteUserRankVO> selectInviteUserRankVOByDTO(IPage page, UserInviteDTO userInviteDTO) {
        return userMapper.selectInviteUserRankVOByDTO(page, userInviteDTO);
    }

    @Override
    public List<InviteUserVO> selectInviteUserVOByPeriod(UserInviteDTO userInviteDTO) {
        return userMapper.selectInviteUserVOByPeriod(userInviteDTO);
    }
}
