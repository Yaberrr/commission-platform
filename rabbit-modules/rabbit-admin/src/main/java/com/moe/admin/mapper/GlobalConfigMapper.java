package com.moe.admin.mapper;

import com.moe.common.core.domain.dto.rule.GlobalConfigDTO;
import com.moe.common.core.domain.vo.rule.GlobalConfigVO;
import org.apache.ibatis.annotations.Param;

public interface GlobalConfigMapper {

    GlobalConfigVO selectGlobalConfigByConfigType(Integer configType);

    int addOrUpdateGlobalConfigByConfigType(@Param("dto") GlobalConfigDTO globalConfigDTO);
}
