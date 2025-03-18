package com.moe.admin.domain.dto.user;

import lombok.Data;

@Data
public class UserInviteDTO {

    private Long userId;

    private String userName;

    private String phoneNumber;

    private String startTime;

    private String endTime;

    private Integer limit;
}
