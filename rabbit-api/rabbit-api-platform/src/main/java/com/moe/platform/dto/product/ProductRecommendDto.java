package com.moe.platform.dto.product;

import com.moe.platform.dto.PlatformPageDTO;
import lombok.Data;

/**
 * 商品推荐dto
 * @author tangyabo
 * @date 2025/3/27
 */
@Data
public class ProductRecommendDto extends PlatformPageDTO {

    //商品id，可选，用于推荐相似商品
    private String productId;

}
