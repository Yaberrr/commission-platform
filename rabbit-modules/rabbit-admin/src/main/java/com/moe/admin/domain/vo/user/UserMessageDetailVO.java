package com.moe.admin.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserMessageDetailVO {

    private Long id;

    private String title;

    private Integer contentForm;

    private String content;

    private String cover;

    private Integer sendType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    private String sendUser;
}
