package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.UserMessageDTO;
import com.moe.admin.domain.vo.user.MessageVO;
import com.moe.admin.domain.vo.user.UserMessageDetailVO;
import com.moe.admin.domain.vo.user.UserMessageVO;

import java.util.List;

public interface UserMessageService {

    Page<UserMessageVO> selectUserMessageVOByDTO(IPage page, UserMessageDTO userMessageDTO);

    UserMessageDetailVO selectUserMessageDetailVOById(Long id);

    int deleteUserMessageById(Long id);

    Page<MessageVO> selectMessageVOByUserId(IPage page, Long userId);
}
