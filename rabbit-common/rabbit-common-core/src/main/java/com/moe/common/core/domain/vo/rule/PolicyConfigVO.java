package com.moe.common.core.domain.vo.rule;

import com.moe.common.core.enums.rule.PolicyType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
public class PolicyConfigVO extends BaseEntity {

    private Long id;

    private PolicyType policyType;

    private String policyName;

    private String policyDesc;

}
