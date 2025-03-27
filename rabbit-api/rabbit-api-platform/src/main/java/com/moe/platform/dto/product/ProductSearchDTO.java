package com.moe.platform.dto.product;

import com.moe.platform.dto.PlatformBaseDTO;
import com.moe.platform.dto.PlatformPageDTO;
import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/3/21
 */
@Data
public class ProductSearchDTO extends PlatformPageDTO {

    //关键词
    private String keyword;
}
