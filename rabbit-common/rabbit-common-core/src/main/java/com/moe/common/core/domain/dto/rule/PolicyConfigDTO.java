package com.moe.common.core.domain.dto.rule;

import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
public class PolicyConfigDTO extends BaseEntity {

    private Long id;

    private Integer policyType;

    private String policyName;

    private String policyDesc;
}
