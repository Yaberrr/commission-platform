package com.moe.admin.service.impl;

import com.moe.admin.mapper.PolicyConfigMapper;
import com.moe.admin.service.PolicyConfigService;
import com.moe.common.core.domain.dto.rule.PolicyConfigDTO;
import com.moe.common.core.domain.vo.rule.PolicyConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyConfigServiceImpl implements PolicyConfigService {

    @Autowired
    private PolicyConfigMapper policyConfigMapper;


    @Override
    public List<PolicyConfigVO> selectPolicyConfigByDTO(PolicyConfigDTO policyConfigDTO) {
        return policyConfigMapper.selectPolicyConfigVOByDTO(policyConfigDTO);
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
