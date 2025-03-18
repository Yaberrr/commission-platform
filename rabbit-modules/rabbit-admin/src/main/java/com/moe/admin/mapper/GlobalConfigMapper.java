package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moe.common.core.domain.config.GlobalConfig;
import com.moe.admin.domain.dto.rule.GlobalConfigDTO;
import com.moe.admin.domain.vo.rule.GlobalConfigVO;
import org.apache.ibatis.annotations.Param;

public interface GlobalConfigMapper extends BaseMapper<GlobalConfig> {

    GlobalConfigVO selectGlobalConfigByConfigType(Integer configType);

    int addOrUpdateGlobalConfigByConfigType(@Param("dto") GlobalConfigDTO globalConfigDTO);
}
