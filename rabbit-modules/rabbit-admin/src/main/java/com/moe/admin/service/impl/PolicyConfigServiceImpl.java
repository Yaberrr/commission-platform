package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.mapper.PolicyConfigMapper;
import com.moe.admin.service.IPolicyConfigService;
import com.moe.admin.domain.dto.rule.PolicyConfigDTO;
import com.moe.admin.domain.vo.rule.PolicyConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyConfigServiceImpl implements IPolicyConfigService {

    @Autowired
    private PolicyConfigMapper policyConfigMapper;


    @Override
    public Page<PolicyConfigVO> selectPolicyConfigByDTO(IPage page, PolicyConfigDTO policyConfigDTO) {
        return policyConfigMapper.selectPolicyConfigVOByDTO(page, policyConfigDTO);
    }

    @Override
    public PolicyConfigVO selectPolicyConfigById(Long id) {
        return policyConfigMapper.selectPolicyConfigVOById(id);
    }

    @Override
    public int insertPolicyConfig(PolicyConfigDTO policyConfigDTO) {
        return policyConfigMapper.insertPolicyConfigVOByDTO(policyConfigDTO);
    }

    @Override
    public int updatePolicyConfig(PolicyConfigDTO policyConfigDTO) {
        return policyConfigMapper.updatePolicyConfigVOByDTO(policyConfigDTO);
    }

    @Override
    public int deletePolicyConfig(Long id) {
        return policyConfigMapper.deletePolicyConfigVOByDTO(id);
    }
}
