package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moe.admin.domain.dto.user.UserMessageAddDTO;
import com.moe.admin.domain.vo.user.MessageVO;
import com.moe.admin.mapper.SystemMessageMapper;
import com.moe.admin.mapper.UserMessageMapper;
import com.moe.admin.service.UserMessageService;
import com.moe.admin.domain.dto.user.UserMessageDTO;
import com.moe.admin.domain.vo.user.UserMessageDetailVO;
import com.moe.admin.domain.vo.user.UserMessageVO;
import com.moe.common.core.domain.message.SystemMessage;
import com.moe.common.core.enums.message.MessageContentType;
import com.moe.common.core.enums.message.MessageSendType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class UserMessageServiceImpl implements UserMessageService {

    @Autowired
    private SystemMessageMapper systemMessageMapper;

    @Autowired
    private UserMessageMapper userMessageMapper;

    @Override
    public Page<UserMessageVO> selectUserMessageVOByDTO(IPage page, UserMessageDTO userMessageDTO) {
        return systemMessageMapper.selectUserMessageVOByDTO(page, userMessageDTO);
    }

    @Override
    public UserMessageDetailVO selectUserMessageDetailVOById(Long id) {
        return systemMessageMapper.selectUserMessageDetailVOById(id);
    }

    @Override
    public int deleteUserMessageById(Long id) {
        return systemMessageMapper.deleteById(id);
    }

    @Override
    public Page<MessageVO> selectMessageVOByUserId(IPage page, Long userId) {
        return userMessageMapper.selectMessageByUserId(page, userId);
    }

    @Override
    public int addUserMessageByDTO(UserMessageAddDTO userMessageAddDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = new ObjectMapper();
        SystemMessage systemMessage = new SystemMessage();
        BeanUtils.copyProperties(userMessageAddDTO, systemMessage);
//        systemMessage.setContentForm(MessageContentType.fromCode(userMessageAddDTO.getContentForm()));
//        systemMessage.setSendType(MessageSendType.fromCode(userMessageAddDTO.getSendType()));
        try {
            systemMessage.setSendTime(sdf.parse(userMessageAddDTO.getSendTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (userMessageAddDTO.getSendUser() != null) {
            try {
                systemMessage.setSendUser(objectMapper.writeValueAsString(userMessageAddDTO.getSendUser()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return systemMessageMapper.insert(systemMessage);
    }

    @Override
    public int udpateUserMessageByDTO(UserMessageAddDTO userMessageAddDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = new ObjectMapper();
        SystemMessage systemMessage = new SystemMessage();
        BeanUtils.copyProperties(userMessageAddDTO, systemMessage);
//        systemMessage.setContentForm(MessageContentType.fromCode(userMessageAddDTO.getContentForm()));
//        systemMessage.setSendType(MessageSendType.fromCode(userMessageAddDTO.getSendType()));
        try {
            systemMessage.setSendTime(sdf.parse(userMessageAddDTO.getSendTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (userMessageAddDTO.getSendUser() != null) {
            try {
                systemMessage.setSendUser(objectMapper.writeValueAsString(userMessageAddDTO.getSendUser()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return systemMessageMapper.updateById(systemMessage);
    }
}
