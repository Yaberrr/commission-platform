package com.moe.common.core.domain.dto.user;

import lombok.Data;

@Data
public class UserMessageUpdateDTO {
    private Long id;

    private String title;

    private Integer contentForm;

    private String content;

    private String cover;

    private Integer sendType;

    private String sendTime;

    private String sendUser;
}
