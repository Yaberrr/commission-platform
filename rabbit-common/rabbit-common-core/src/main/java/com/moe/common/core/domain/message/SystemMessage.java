package com.moe.common.core.domain.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.enums.message.MessageContentType;
import com.moe.common.core.enums.message.MessageSendType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;
import java.util.Date;

/**
 * 系统消息
 */
@Data
@TableName("rb_system_message")
public class SystemMessage extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 消息标题
    private String title;

    // 消息内容
    private String content;

    // 消息格式 1上下图文 2左右图文 3纯文字
    private MessageContentType contentForm;

    // 消息图片
    private String cover;

    // 发送时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    // 发送人群  1全体用户 2指定用户
    private MessageSendType sendType;

    // 发送用户
    private String sendUser;

    // 发送状态  0待发送 1已发送
    private Integer status;
}
