package com.moe.admin.service;

import com.moe.admin.domain.dto.user.UserMessageDTO;
import com.moe.admin.domain.vo.user.UserMessageDetailVO;
import com.moe.admin.domain.vo.user.UserMessageVO;

import java.util.List;

public interface UserMessageService {

    List<UserMessageVO> selectUserMessageVOByDTO(UserMessageDTO userMessageDTO);

    UserMessageDetailVO selectUserMessageDetailVOById(Long id);

    int deleteUserMessageById(Long id);
}
