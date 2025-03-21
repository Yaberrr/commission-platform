package com.moe.product.domain.dto;

import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 商品搜索
 * @author tangyabo
 * @date 2025/3/21
 */
@Data
public class ProductSearchDTO {

    @NotNull(message = "平台类型不可为空")
    private PlatformType platformType;

    @NotEmpty(message = "关键词不可为空")
    private String keyword;

    public void setPlatformType(Integer code) {
        this.platformType = PlatformType.fromCode(code);
    }

}
