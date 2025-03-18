package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.rule.PolicyConfigDTO;
import com.moe.admin.domain.vo.rule.PolicyConfigVO;

import java.util.List;

public interface PolicyConfigService {

    Page<PolicyConfigVO> selectPolicyConfigByDTO(IPage page, PolicyConfigDTO policyConfigDTO);

    PolicyConfigVO selectPolicyConfigById(Long id);

    int insertPolicyConfig(PolicyConfigDTO policyConfigDTO);

    int updatePolicyConfig(PolicyConfigDTO policyConfigDTO);

    int deletePolicyConfig(Long id);
}
