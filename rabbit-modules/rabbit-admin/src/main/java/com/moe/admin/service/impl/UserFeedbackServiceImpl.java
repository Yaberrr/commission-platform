package com.moe.admin.service.impl;

import com.moe.admin.mapper.UserFeedbackMapper;
import com.moe.admin.service.UserFeedbackService;
import com.moe.common.core.domain.dto.user.UserFeedBackDTO;
import com.moe.common.core.domain.dto.user.UserFeedbackUpdateDTO;
import com.moe.common.core.domain.vo.user.UserFeedbackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {

    @Autowired
    private UserFeedbackMapper userFeedbackMapper;

    @Override
    public List<UserFeedbackVO> selectUserFeedbackByDTO(UserFeedBackDTO userFeedBackDTO) {
        return userFeedbackMapper.selectUserFeedbackByDTO(userFeedBackDTO);
    }

    @Override
    public UserFeedbackVO selectUserFeedbackById(Long id) {
        return userFeedbackMapper.selectUserFeedbackById(id);
    }

    @Override
    public int updateUserFeedback(UserFeedbackUpdateDTO userFeedbackUpdateDTO) {
        return userFeedbackMapper.updateUserFeedbackById(userFeedbackUpdateDTO);
    }
}
