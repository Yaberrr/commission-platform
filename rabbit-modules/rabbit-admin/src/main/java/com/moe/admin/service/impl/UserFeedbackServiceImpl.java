package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.mapper.UserFeedbackMapper;
import com.moe.admin.service.IUserFeedbackService;
import com.moe.admin.domain.dto.user.UserFeedBackDTO;
import com.moe.admin.domain.dto.user.UserFeedbackUpdateDTO;
import com.moe.admin.domain.vo.user.UserFeedbackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFeedbackServiceImpl implements IUserFeedbackService {

    @Autowired
    private UserFeedbackMapper userFeedbackMapper;

    @Override
    public Page<UserFeedbackVO> selectUserFeedbackByDTO(IPage page, UserFeedBackDTO userFeedBackDTO) {
        return userFeedbackMapper.selectUserFeedbackByDTO(page, userFeedBackDTO);
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
