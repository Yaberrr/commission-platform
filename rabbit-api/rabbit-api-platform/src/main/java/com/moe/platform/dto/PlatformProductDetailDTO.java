package com.moe.platform.dto;

import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/3/27
 */
@Data
public class PlatformProductDetailDTO {

    //商品id
    private String productId;

    //平台类型
    private PlatformType platformType;

    //查询参数
    private String searchParam;

}
