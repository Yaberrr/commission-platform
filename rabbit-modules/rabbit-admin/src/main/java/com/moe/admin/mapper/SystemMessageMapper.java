package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.UserMessageDTO;
import com.moe.common.core.domain.message.UserMessage;
import com.moe.admin.domain.vo.user.UserMessageDetailVO;
import com.moe.admin.domain.vo.user.UserMessageVO;
import org.apache.ibatis.annotations.Param;

public interface SystemMessageMapper extends BaseMapper<UserMessage> {

    Page<UserMessageVO> selectUserMessageVOByDTO(IPage page, @Param("dto") UserMessageDTO userMessageDTO);

    UserMessageDetailVO selectUserMessageDetailVOById(@Param("id") Long id);
}
