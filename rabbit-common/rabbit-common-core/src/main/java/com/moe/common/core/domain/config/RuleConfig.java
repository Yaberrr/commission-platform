package com.moe.common.core.domain.config;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.config.RuleType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 规则配置
 */
@Data
@TableName("rb_rule_config")
public class RuleConfig extends BaseEntity {
    @TableId
    private Long id;

    // 规则类型
    private RuleType ruleType;

    // 规则名称
    private String ruleName;

    // 规则描述
    private String ruleDesc;
}
