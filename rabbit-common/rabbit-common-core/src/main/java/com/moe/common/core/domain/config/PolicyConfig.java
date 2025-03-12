package com.moe.common.core.domain.config;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.config.PolicyType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 政策配置
 */
@Data
@TableName("rb_policy_config")
public class PolicyConfig extends BaseEntity {
    @TableId
    private Long id;

    // 政策类型
    private PolicyType policyType;

    // 政策名称
    private String policyName;

    // 政策描述
    private String policyDesc;
}
