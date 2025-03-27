package com.moe.product.domain.dto;

import com.moe.common.core.enums.platform.PlatformType;
import com.moe.platform.dto.PlatformBaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 商品搜索
 * @author tangyabo
 * @date 2025/3/21
 */
@Data
public class ProductSearchDTO extends PlatformBaseDTO {

    @NotEmpty(message = "关键词不可为空")
    private String keyword;

}
