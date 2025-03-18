package com.moe.admin.domain.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserMessageVO {

    private Long id;

    private String title;

    private Integer contentForm;

    private Date sendTime;

    private Integer sendType;

    private Integer status;
}
