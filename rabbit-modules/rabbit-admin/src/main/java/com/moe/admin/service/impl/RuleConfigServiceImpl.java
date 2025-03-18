package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.mapper.RuleConfigMapper;
import com.moe.admin.service.RuleConfigService;
import com.moe.admin.domain.dto.rule.RuleConfigDTO;
import com.moe.admin.domain.vo.rule.RuleConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleConfigServiceImpl implements RuleConfigService {

    @Autowired
    private RuleConfigMapper ruleConfigMapper;

    @Override
    public Page<RuleConfigVO> selectRuleConfigVOByUser(IPage page, RuleConfigDTO ruleConfigDTO) {
        return ruleConfigMapper.selectRuleConfigByDTO(page, ruleConfigDTO);
    }

    @Override
    public RuleConfigVO selectRuleConfigVOById(Long id) {
        return ruleConfigMapper.selectRuleConfigById(id);
    }

    @Override
    public int addRuleConfig(RuleConfigDTO ruleConfigDTO) {
        return ruleConfigMapper.insertRuleConfig(ruleConfigDTO);
    }

    @Override
    public int updateRuleConfig(RuleConfigDTO ruleConfigDTO) {
        return ruleConfigMapper.updateRuleConfig(ruleConfigDTO);
    }

    @Override
    public int deleteRuleConfigById(Long id) {
        return ruleConfigMapper.deleteById(id);
    }
}
