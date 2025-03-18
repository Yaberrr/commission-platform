package com.moe.admin.service;

import com.moe.common.core.domain.dto.user.UserFeedBackDTO;
import com.moe.common.core.domain.dto.user.UserFeedbackUpdateDTO;
import com.moe.common.core.domain.user.UserFeedback;
import com.moe.common.core.domain.vo.user.UserFeedbackVO;

import java.util.List;

public interface UserFeedbackService {

    List<UserFeedbackVO> selectUserFeedbackByDTO(UserFeedBackDTO userFeedBackDTO);

    UserFeedbackVO selectUserFeedbackById(Long id);

    int updateUserFeedback(UserFeedbackUpdateDTO userFeedbackUpdateDTO);
}
