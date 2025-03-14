package com.moe.admin.service;

import com.moe.common.core.domain.dto.user.UserMessageDTO;
import com.moe.common.core.domain.vo.user.UserMessageDetailVO;
import com.moe.common.core.domain.vo.user.UserMessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMessageService {

    List<UserMessageVO> selectUserMessageVOByDTO(UserMessageDTO userMessageDTO);

    UserMessageDetailVO selectUserMessageDetailVOById(Long id);
}
