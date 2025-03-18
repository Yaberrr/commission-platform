package com.moe.admin.service;

import com.moe.admin.domain.dto.user.UserFeedBackDTO;
import com.moe.admin.domain.dto.user.UserFeedbackUpdateDTO;
import com.moe.admin.domain.vo.user.UserFeedbackVO;

import java.util.List;

public interface UserFeedbackService {

    List<UserFeedbackVO> selectUserFeedbackByDTO(UserFeedBackDTO userFeedBackDTO);

    UserFeedbackVO selectUserFeedbackById(Long id);

    int updateUserFeedback(UserFeedbackUpdateDTO userFeedbackUpdateDTO);
}
