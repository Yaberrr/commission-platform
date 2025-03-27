package com.moe.product.domain.dto;

import com.moe.platform.dto.PlatformBaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author tangyabo
 * @date 2025/3/27
 */
@Data
public class ProductDetailDTO extends PlatformBaseDTO {

    //商品id
    @NotEmpty(message = "商品id不可为空")
    private String productId;

    //查询参数
    private String searchParam;

}
