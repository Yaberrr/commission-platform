package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moe.admin.domain.dto.rule.RuleConfigDTO;
import com.moe.admin.domain.vo.rule.RuleConfigVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RuleConfigMapper extends BaseMapper {

    List<RuleConfigVO> selectRuleConfigByDTO(@Param("dto") RuleConfigDTO ruleConfigDTO);

    RuleConfigVO selectRuleConfigById(@Param("id") Long id);

    int insertRuleConfig(@Param("dto") RuleConfigDTO ruleConfigDTO);

    int updateRuleConfig(@Param("dto") RuleConfigDTO ruleConfigDTO);
}
