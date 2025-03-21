package com.moe.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.vo.ProductVO;
import com.moe.product.domain.dto.ProductListDTO;
import com.moe.product.domain.dto.ProductSearchDTO;

/**
 * 商品
 * @author tangyabo
 * @date 2025/3/21
 */
public interface ProductService {

    /**
     * 商品列表
     * @param page
     * @param dto
     * @return
     */
    TableDataInfo<ProductVO> productList(IPage page, ProductListDTO dto);

    /**
     * 商品搜索
     * @param page
     * @param dto
     * @return
     */
    TableDataInfo<ProductVO> productSearch(IPage page, ProductSearchDTO dto);

}
