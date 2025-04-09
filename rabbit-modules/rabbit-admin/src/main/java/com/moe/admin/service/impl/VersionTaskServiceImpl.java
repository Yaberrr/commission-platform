package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.VersionTaskDTO;
import com.moe.admin.domain.vo.user.VersionTaskVO;
import com.moe.admin.mapper.VersionTaskMapper;
import com.moe.admin.service.IVersionTaskService;
import com.moe.common.core.domain.user.VersionTask;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionTaskServiceImpl implements IVersionTaskService {
    @Autowired
    private VersionTaskMapper versionTaskMapper;

    @Override
    public Page<VersionTaskVO> selectAllVersionTask(IPage page) {
        return versionTaskMapper.selectAllVersionTask(page);
    }

    @Override
    public int addVersionTask(VersionTaskDTO versionTaskDTO) {
        VersionTask versionTask = new VersionTask();
        BeanUtils.copyProperties(versionTaskDTO, versionTask);
        versionTaskMapper.insert(versionTask);
        return 0;
    }

    @Override
    public int editVersionTask(VersionTaskDTO versionTaskDTO) {
        VersionTask versionTask = new VersionTask();
        BeanUtils.copyProperties(versionTaskDTO, versionTask);
        versionTaskMapper.updateById(versionTask);
        return 0;
    }

    @Override
    public int deleteVersionTask(Long id) {
        return versionTaskMapper.deleteById(id);
    }
}
