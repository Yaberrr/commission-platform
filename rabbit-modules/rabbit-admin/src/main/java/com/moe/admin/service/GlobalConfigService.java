package com.moe.admin.service;

import com.moe.common.core.domain.dto.rule.GlobalConfigDTO;
import com.moe.common.core.domain.vo.rule.GlobalConfigVO;

import java.util.List;

public interface GlobalConfigService {

    GlobalConfigVO selectGlobalConfigByConfigType(Integer configType);

    int updateGlobalConfigByConfigType(GlobalConfigDTO globalConfigDTO);
}
