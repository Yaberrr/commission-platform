package com.moe.admin.service;

import com.moe.admin.domain.dto.rule.RuleConfigDTO;
import com.moe.admin.domain.vo.rule.RuleConfigVO;

import java.util.List;

public interface RuleConfigService {

    List<RuleConfigVO> selectRuleConfigVOByUser(RuleConfigDTO ruleConfigDTO);

    RuleConfigVO selectRuleConfigVOById(Long id);

    int addRuleConfig(RuleConfigDTO ruleConfigDTO);

    int updateRuleConfig(RuleConfigDTO ruleConfigDTO);

    int deleteRuleConfigById(Long id);
}
