package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.UserFeedBackDTO;
import com.moe.admin.domain.dto.user.UserFeedbackUpdateDTO;
import com.moe.admin.domain.vo.user.UserFeedbackVO;

import java.util.List;

public interface UserFeedbackService {

    Page<UserFeedbackVO> selectUserFeedbackByDTO(IPage page, UserFeedBackDTO userFeedBackDTO);

    UserFeedbackVO selectUserFeedbackById(Long id);

    int updateUserFeedback(UserFeedbackUpdateDTO userFeedbackUpdateDTO);
}
