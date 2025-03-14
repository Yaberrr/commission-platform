package com.moe.admin.mapper;

import com.moe.common.core.domain.dto.rule.RuleConfigDTO;
import com.moe.common.core.domain.vo.rule.RuleConfigVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RuleConfigMapper {

    List<RuleConfigVO> selectRuleConfigByDTO(@Param("dto") RuleConfigDTO ruleConfigDTO);

    RuleConfigVO selectRuleConfigById(@Param("id") Long id);

    int insertRuleConfig(@Param("dto") RuleConfigDTO ruleConfigDTO);

    int updateRuleConfig(@Param("dto") RuleConfigDTO ruleConfigDTO);
}
