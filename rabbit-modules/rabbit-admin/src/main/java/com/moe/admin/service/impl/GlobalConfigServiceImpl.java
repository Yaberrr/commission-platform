package com.moe.admin.service.impl;

import com.moe.admin.mapper.GlobalConfigMapper;
import com.moe.admin.service.GlobalConfigService;
import com.moe.common.core.domain.dto.rule.GlobalConfigDTO;
import com.moe.common.core.domain.vo.rule.GlobalConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalConfigServiceImpl implements GlobalConfigService {

    @Autowired
    private GlobalConfigMapper globalConfigMapper;

    @Override
    public GlobalConfigVO selectGlobalConfigByConfigType(Integer configType) {
        return globalConfigMapper.selectGlobalConfigByConfigType(configType);
    }

    @Override
    public int updateGlobalConfigByConfigType(GlobalConfigDTO globalConfigDTO) {
        return globalConfigMapper.addOrUpdateGlobalConfigByConfigType(globalConfigDTO);
    }
}
