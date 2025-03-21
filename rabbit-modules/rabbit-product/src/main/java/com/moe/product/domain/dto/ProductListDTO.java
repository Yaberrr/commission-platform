package com.moe.product.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tangyabo
 * @date 2025/3/20
 */
@Data
public class ProductListDTO {

    //商品组id
    @NotNull(message = "商品组不可为空")
    private Long groupId;

}
