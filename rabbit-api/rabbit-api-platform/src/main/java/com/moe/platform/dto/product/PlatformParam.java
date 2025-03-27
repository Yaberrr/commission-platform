package com.moe.platform.dto.product;

import com.moe.common.core.enums.platform.PlatformDictType;
import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

/**
 * 商品用-平台参数
 * @author tangyabo
 * @date 2025/3/19
 */
@Data
public class PlatformParam {

    //平台类型
    private PlatformType platformType;

    //参数类型
    private PlatformDictType dictType;

    //对应值
    private String dictValue;

}
