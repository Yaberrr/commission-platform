package com.moe.admin.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class InviteUserRankVO {

    private Long id;

    private Long userId;

    private String userName;

    private String phoneNumber;

    private Integer orderCount;

    private Integer periodInviteCount;

    private Integer inviteCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
}
