package com.moe.product.domain.dto;

import com.moe.platform.dto.PlatformBaseDTO;
import lombok.Data;

/**
 * 商品收藏
 *
 * @author liang.lu
 * 2025/4/14 11:02:00
 */
@Data
public class ProductFavoriteDTO extends PlatformBaseDTO {
    //商品id
    private String productId;
}
