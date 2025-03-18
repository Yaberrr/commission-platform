package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.UserFeedBackDTO;
import com.moe.admin.domain.dto.user.UserFeedbackUpdateDTO;
import com.moe.admin.domain.vo.user.UserFeedbackVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFeedbackMapper {

    Page<UserFeedbackVO> selectUserFeedbackByDTO(IPage page, @Param("dto") UserFeedBackDTO userFeedBackDTO);

    UserFeedbackVO selectUserFeedbackById(@Param("id") Long id);

    int updateUserFeedbackById(@Param("dto")UserFeedbackUpdateDTO userFeedbackUpdateDTO);
}
