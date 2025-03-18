package com.moe.admin.service;

import com.moe.admin.domain.dto.rule.PolicyConfigDTO;
import com.moe.admin.domain.vo.rule.PolicyConfigVO;

import java.util.List;

public interface PolicyConfigService {

    List<PolicyConfigVO> selectPolicyConfigByDTO(PolicyConfigDTO policyConfigDTO);

    PolicyConfigVO selectPolicyConfigById(Long id);

    int insertPolicyConfig(PolicyConfigDTO policyConfigDTO);

    int updatePolicyConfig(PolicyConfigDTO policyConfigDTO);

    int deletePolicyConfig(Long id);
}
