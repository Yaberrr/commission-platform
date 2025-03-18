package com.moe.common.core.domain.vo.user;

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

    private Date registerTime;

    private Date lastLoginTime;
}
