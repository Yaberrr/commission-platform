package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.vo.user.VersionTaskVO;
import com.moe.common.core.domain.user.VersionTask;

public interface VersionTaskMapper extends BaseMapper<VersionTask> {

    Page<VersionTaskVO> selectAllVersionTask(IPage page);
}
