package com.moe.common.core.domain.vo.rule;

import com.moe.common.core.enums.config.RuleType;
import lombok.Data;

import java.util.Date;

@Data
public class RuleConfigVO {

    private Long id;

    private RuleType ruleType;

    private String ruleName;

    private String ruleDesc;

    private Date updateTime;

    private String updateBy;
}
