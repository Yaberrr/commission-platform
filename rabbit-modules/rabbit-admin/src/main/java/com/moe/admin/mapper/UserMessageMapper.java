package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.vo.user.MessageVO;
import com.moe.common.core.domain.message.UserMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMessageMapper extends BaseMapper<UserMessage> {

    Page<MessageVO> selectMessageByUserId(IPage page, @Param("userId")Long userId);
}
