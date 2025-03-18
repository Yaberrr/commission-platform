package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moe.admin.domain.dto.user.UserMessageDTO;
import com.moe.common.core.domain.message.UserMessage;
import com.moe.admin.domain.vo.user.UserMessageDetailVO;
import com.moe.admin.domain.vo.user.UserMessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMessageMapper extends BaseMapper<UserMessage> {

    List<UserMessageVO> selectUserMessageVOByDTO(@Param("dto") UserMessageDTO userMessageDTO);

    UserMessageDetailVO selectUserMessageDetailVOById(@Param("id") Long id);
}
