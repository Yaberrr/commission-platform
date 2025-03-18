package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.mapper.UserMessageMapper;
import com.moe.admin.service.UserMessageService;
import com.moe.admin.domain.dto.user.UserMessageDTO;
import com.moe.admin.domain.vo.user.UserMessageDetailVO;
import com.moe.admin.domain.vo.user.UserMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMessageServiceImpl implements UserMessageService {

    @Autowired
    private UserMessageMapper userMessageMapper;
    @Override
    public Page<UserMessageVO> selectUserMessageVOByDTO(IPage page, UserMessageDTO userMessageDTO) {
        return userMessageMapper.selectUserMessageVOByDTO(page, userMessageDTO);
    }

    @Override
    public UserMessageDetailVO selectUserMessageDetailVOById(Long id) {
        return userMessageMapper.selectUserMessageDetailVOById(id);
    }

    @Override
    public int deleteUserMessageById(Long id) {
        return userMessageMapper.deleteById(id);
    }
}
