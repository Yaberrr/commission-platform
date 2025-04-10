package com.moe.admin.domain.dto.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class InviteTemplateDTO {
    @JsonIgnore
    private Long id;

    private List<String> cover;

    private List<Integer> invitePlatform;

    private String inviteDesc;
}
