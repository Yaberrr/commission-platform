package com.moe.common.core.domain.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.message.MessageType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 用户消息
 */
@Data
@TableName("rb_user_message")
public class UserMessage extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户id
    private Long userId;

    // 消息类型 1订单 2元宝宝
    private MessageType messageType;

    // 消息标题
    private String title;

    // 消息内容
    private String content;

    // 消息图片
    private String cover;

    // 流水记录id
    private Long walletRecordId;
}
