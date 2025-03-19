package com.moe.admin.domain.dto.rule;

import lombok.Data;

import java.util.List;

@Data
public class InviteModelDTO {
    private Long id;

    private List<String> cover;

    private List<Integer> invitePlatform;

    private String inviteDesc;
}
