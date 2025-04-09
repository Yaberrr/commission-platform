package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.product.ProductGroupDTO;
import com.moe.admin.domain.dto.product.ProductGroupUpdateDTO;
import com.moe.admin.domain.vo.product.ProductGroupDetailVO;
import com.moe.admin.domain.vo.product.ProductGroupDictVO;
import com.moe.admin.domain.vo.product.ProductGroupVO;

import java.util.List;

public interface IProductGroupService {

    Page<ProductGroupVO> selectProductGroupVOByDTO(IPage page, ProductGroupDTO productGroupDTO);

    ProductGroupDetailVO selectProductGroupDetailById(Long id);

    int addProductGroup(ProductGroupUpdateDTO productGroupUpdateDTO);

    int editProductGroup(ProductGroupUpdateDTO productGroupUpdateDTO);

    int deleteProductGroup(List<Long> ids);

    List<ProductGroupDictVO> getAllProductGroupDictVO();
}

