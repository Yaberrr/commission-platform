package com.moe.admin.domain.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserFeedbackUpdateDTO {

    private Long id;

    private String replyContent;

    private String replyImg;
}
