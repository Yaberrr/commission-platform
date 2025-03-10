package com.moe.common.core.domain.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;
import java.util.Date;

/**
 * 用户反馈
 */
@Data
@TableName("rb_user_feedback")
public class UserFeedback extends BaseEntity {
    @TableId
    private Long id;

    // 用户id
    private Long userId;

    // 反馈内容
    private String feedbackContent;

    // 反馈图片
    private String feedbackImg;

    // 反馈时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date feedbackTime;

    // 反馈次数
    private Integer feedbackCount;

    // 回复内容
    private String replyContent;

    // 回复图片
    private String replyImg;

    // 状态  0未回复 1已回复
    private Integer status;
}
