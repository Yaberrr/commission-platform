package com.moe.admin.domain.dto.rule;

import lombok.Data;

@Data
public class RuleConfigDTO {

    private Long id;

    private Integer ruleType;

    private String ruleName;

    private String ruleDesc;
}
