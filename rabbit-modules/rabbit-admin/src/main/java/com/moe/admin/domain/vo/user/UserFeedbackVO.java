package com.moe.admin.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class UserFeedbackVO {

    private Long id;

    private Long userId;

    private String userName;

    private String wechat;

    private String phoneNumber;

    private String feedbackContent;

    private String feedbackImgStr;

    private List<String> feedbackImg;

    private String replyContent;

    private String replyImg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date feedbackTime;

    private Integer feedbackCount;

    private Integer status;

    public List<String> getFeedbackImg() {
        return feedbackImgStr == null ? null : Arrays.asList(feedbackImgStr.split(","));
    }
}
