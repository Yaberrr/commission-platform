package com.moe.common.core.domain.vo.user;

import lombok.Data;

@Data
public class UserMessageDetailVO {

    private Long id;

    private String title;

    private Integer contentForm;

    private String content;

    private String cover;

    private Integer sendType;

    private String sendTime;

    private String sendUser;
}
