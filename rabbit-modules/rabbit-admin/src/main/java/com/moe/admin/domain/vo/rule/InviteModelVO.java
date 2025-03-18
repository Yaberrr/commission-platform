package com.moe.admin.domain.vo.rule;

import lombok.Data;

import java.util.List;

@Data
public class InviteModelVO {

    private Long id;

    private String cover;

    private List<Integer> invitePlatform;

    private String inviteDesc;
}
