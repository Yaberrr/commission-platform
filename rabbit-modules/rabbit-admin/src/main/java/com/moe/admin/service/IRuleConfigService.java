package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.rule.RuleConfigDTO;
import com.moe.admin.domain.vo.rule.RuleConfigVO;

public interface IRuleConfigService {

    Page<RuleConfigVO> selectRuleConfigVOByUser(IPage page, RuleConfigDTO ruleConfigDTO);

    RuleConfigVO selectRuleConfigVOById(Long id);

    int addRuleConfig(RuleConfigDTO ruleConfigDTO);

    int updateRuleConfig(RuleConfigDTO ruleConfigDTO);

    int deleteRuleConfigById(Long id);
}
