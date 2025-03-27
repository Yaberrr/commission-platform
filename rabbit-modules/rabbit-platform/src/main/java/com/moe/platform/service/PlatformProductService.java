package com.moe.platform.service;

import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.dto.product.*;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;

/**
 * 商品
 * @author tangyabo
 * @date 2025/3/19
 */
public interface PlatformProductService {

    /**
     * 商品列表
     * @param dto
     * @param param
     * @return
     */
    TableDataInfo<ProductVO> productList(PlatformProductDTO dto, PlatformParam param);

    /**
     * 商品搜索
     * @param dto
     * @return
     */
    TableDataInfo<ProductVO> productSearch(ProductSearchDTO dto);

    /**
     * 商品详情
     * @param dto
     * @return
     */
    ProductDetailVO productDetail(ProductDetailDTO dto);

    /**
     * 商品推荐
     * @param dto
     * @return
     */
    TableDataInfo<ProductVO> productRecommend(ProductRecommendDto dto);
}
