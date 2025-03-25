package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.product.ProductGroupDTO;
import com.moe.admin.domain.vo.product.ProductGroupVO;
import com.moe.common.core.domain.product.ProductGroup;
import org.apache.ibatis.annotations.Param;

public interface ProductGroupMapper extends BaseMapper<ProductGroup> {

    Page<ProductGroupVO> selectProductGroupVOByDTO(IPage page, @Param("dto") ProductGroupDTO productGroupDTO);



}
