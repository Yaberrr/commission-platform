package com.moe.admin.service.impl;

import com.moe.admin.mapper.UserMessageMapper;
import com.moe.admin.service.UserMessageService;
import com.moe.common.core.domain.dto.user.UserMessageDTO;
import com.moe.common.core.domain.message.UserMessage;
import com.moe.common.core.domain.vo.user.UserMessageDetailVO;
import com.moe.common.core.domain.vo.user.UserMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMessageServiceImpl implements UserMessageService {

    @Autowired
    private UserMessageMapper userMessageMapper;
    @Override
    public List<UserMessageVO> selectUserMessageVOByDTO(UserMessageDTO userMessageDTO) {
        return userMessageMapper.selectUserMessageVOByDTO(userMessageDTO);
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
