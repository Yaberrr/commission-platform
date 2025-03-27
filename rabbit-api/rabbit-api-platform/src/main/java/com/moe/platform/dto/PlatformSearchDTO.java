package com.moe.platform.dto;

import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.web.page.TableSupport;
import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/3/21
 */
@Data
public class PlatformSearchDTO {

    //页号
    private int pageNum;

    //页大小
    private int pageSize;

    //平台类型
    private PlatformType platformType;

    //关键词
    private String keyword;
}
