package com.moe.admin.domain.vo.rule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.enums.config.RuleType;
import lombok.Data;

import java.util.Date;

@Data
public class RuleConfigVO {

    private Long id;

    private RuleType ruleType;

    private String ruleName;

    private String ruleDesc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String updateBy;
}
