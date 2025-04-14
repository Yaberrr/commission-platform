package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.common.core.domain.IndexDiy;
import com.moe.common.core.mapper.BaseMapperPlus;

/**
 * @author tangyabo
 * @date 2025/4/11
 */
public interface IndexDiyMapper extends BaseMapperPlus<IndexDiyMapper,IndexDiy,IndexDiy> {

    Page<IndexDiy> diyIndexList(IPage page);

}
