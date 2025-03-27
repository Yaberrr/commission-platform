package com.moe.platform.dto.product;

import com.moe.platform.dto.PlatformBaseDTO;
import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/3/27
 */
@Data
public class ProductDetailDTO extends PlatformBaseDTO {

    //商品id
    private String productId;

    //查询参数
    private String searchParam;

}
