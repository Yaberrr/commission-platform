package com.moe.common.core.domain.dto.user;

import lombok.Data;

@Data
public class UserFeedBackDTO {

    private Long userId;

    private String userName;

    private String wechat;

    private String phoneNumber;

    private String minFeedbackTime;

    private String maxFeedbackTime;

    private Integer status;
}
