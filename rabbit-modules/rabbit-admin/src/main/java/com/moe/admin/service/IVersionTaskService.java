package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.VersionTaskDTO;
import com.moe.admin.domain.vo.user.VersionTaskVO;

public interface IVersionTaskService {

    Page<VersionTaskVO> selectAllVersionTask(IPage page);

    int addVersionTask(VersionTaskDTO versionTaskDTO);

    int editVersionTask(VersionTaskDTO versionTaskDTO);

    int deleteVersionTask(Long id);
}
