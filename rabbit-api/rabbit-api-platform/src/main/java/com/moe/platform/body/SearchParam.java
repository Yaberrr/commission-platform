package com.moe.platform.body;

import com.moe.common.core.enums.platform.PlatformDictType;
import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

/**
 * 平台参数
 * @author tangyabo
 * @date 2025/3/19
 */
@Data
public class SearchParam {

    //平台类型
    private PlatformType platformType;

    //tag类型
    private PlatformDictType tagType;

    //对应值
    private String tagValue;

}
