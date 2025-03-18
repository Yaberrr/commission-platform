package com.moe.admin.mapper;

import com.moe.common.core.domain.dto.rule.PolicyConfigDTO;
import com.moe.common.core.domain.vo.rule.PolicyConfigVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PolicyConfigMapper {

    public List<PolicyConfigVO> selectPolicyConfigVOByDTO(@Param("dto") PolicyConfigDTO policyConfigDTO);

    public PolicyConfigVO selectPolicyConfigVOById(@Param("id") Long id);

    int insertPolicyConfigVOByDTO(@Param("dto") PolicyConfigDTO policyConfigDTO);

    int updatePolicyConfigVOByDTO(@Param("dto") PolicyConfigDTO policyConfigDTO);

    int deletePolicyConfigVOByDTO(@Param("id") Long id);
}
