package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.IndexDiySaveDTO;
import com.moe.common.core.domain.IndexDiy;

/**
 * @author tangyabo
 * @date 2025/4/11
 */
public interface IIndexDiyService {

    /**
     * 装修列表
     * @param page
     * @return
     */
    Page<IndexDiy> indexDiyList(IPage page);

    /**
     * 装修详情
     * @param id
     * @return
     */
    IndexDiy indexDiyDetail(Long id);

    /**
     * 保存装修
     */
    void saveIndexDiy(IndexDiySaveDTO dto);

    /**
     * 取消发布
     * @param id
     */
    void cancelIndexDiy(Long id);

    /**
     * 删除装修
     * @param id
     */
    void deleteIndexDiy(Long id);
}
