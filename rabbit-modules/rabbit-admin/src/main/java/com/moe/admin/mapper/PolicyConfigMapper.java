package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.rule.PolicyConfigDTO;
import com.moe.admin.domain.vo.rule.PolicyConfigVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PolicyConfigMapper {

    public Page<PolicyConfigVO> selectPolicyConfigVOByDTO(IPage page, @Param("dto") PolicyConfigDTO policyConfigDTO);

    public PolicyConfigVO selectPolicyConfigVOById(@Param("id") Long id);

    int insertPolicyConfigVOByDTO(@Param("dto") PolicyConfigDTO policyConfigDTO);

    int updatePolicyConfigVOByDTO(@Param("dto") PolicyConfigDTO policyConfigDTO);

    int deletePolicyConfigVOByDTO(@Param("id") Long id);
}
