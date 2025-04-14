package com.moe.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moe.common.core.domain.R;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.dto.product.ProductDetailDTO;
import com.moe.platform.dto.product.ProductRecommendDto;
import com.moe.platform.dto.product.ProductSearchDTO;
import com.moe.platform.vo.PlatformUrlVO;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import com.moe.product.domain.dto.CancelFavoriteDTO;
import com.moe.product.domain.dto.ProductFavoriteDTO;
import com.moe.product.domain.dto.ProductListDTO;

/**
 * 商品
 * @author tangyabo
 * @date 2025/3/21
 */
public interface IProductService {

    /**
     * 商品列表
     * @param page
     * @param dto
     * @return
     */
    R<TableDataInfo<ProductVO>> productList(IPage page, ProductListDTO dto);

    /**
     * 商品搜索
     * @param dto
     * @return
     */
    R<TableDataInfo<ProductVO>> productSearch(ProductSearchDTO dto);

    /**
     * 商品详情
     * @param dto
     * @return
     */
    R<ProductDetailVO> productDetail(ProductDetailDTO dto);

    /**
     * 为你推荐
     * @param dto
     * @return
     */
    R<TableDataInfo<ProductVO>> productRecommend(ProductRecommendDto dto);

    /**
     * 商品链接
     * @param dto
     * @return
     */
    R<PlatformUrlVO> productUrl(ProductDetailDTO dto);

    /**
     * 添加收藏
     * @param dto
     * @return
     */
    R<ProductVO> addFavorite(ProductFavoriteDTO dto);

    /**
     * 取消收藏
     * @param dto
     * @return
     */
    R<Boolean> cancelFavorite(CancelFavoriteDTO dto);
}
