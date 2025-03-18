package com.moe.admin.mapper;

import com.moe.common.core.domain.dto.user.UserFeedBackDTO;
import com.moe.common.core.domain.dto.user.UserFeedbackUpdateDTO;
import com.moe.common.core.domain.user.UserFeedback;
import com.moe.common.core.domain.vo.user.UserFeedbackVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFeedbackMapper {

    List<UserFeedbackVO> selectUserFeedbackByDTO(@Param("dto") UserFeedBackDTO userFeedBackDTO);

    UserFeedbackVO selectUserFeedbackById(@Param("id") Long id);

    int updateUserFeedbackById(@Param("dto")UserFeedbackUpdateDTO userFeedbackUpdateDTO);
}
