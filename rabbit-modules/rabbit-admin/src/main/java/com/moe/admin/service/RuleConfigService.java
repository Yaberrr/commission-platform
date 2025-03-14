package com.moe.admin.service;

import com.moe.common.core.domain.config.RuleConfig;
import com.moe.common.core.domain.dto.rule.RuleConfigDTO;
import com.moe.common.core.domain.vo.rule.RuleConfigVO;

import java.util.List;

public interface RuleConfigService {

    List<RuleConfigVO> selectRuleConfigVOByUser(RuleConfigDTO ruleConfigDTO);

    RuleConfigVO selectRuleConfigVOById(Long id);

    int addRuleConfig(RuleConfigDTO ruleConfigDTO);

    int updateRuleConfig(RuleConfigDTO ruleConfigDTO);
}
