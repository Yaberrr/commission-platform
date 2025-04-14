package com.moe.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moe.common.core.domain.product.ProductFavorite;
import org.apache.ibatis.annotations.Param;

/**
 * 商品收藏
 *
 * @author liang.lu
 * 2025/4/13 14:53:08
 */
public interface ProductFavoriteMapper extends BaseMapper<ProductFavorite> {
//    void deleteByUserIdAndProductId(@Param("userId") Long userId,@Param("productId") String productId);
}
