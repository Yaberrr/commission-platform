package com.moe.platform.dto;

import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.web.page.TableSupport;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tangyabo
 * @date 2025/3/27
 */
@Data
public class PlatformPageDTO extends PlatformBaseDTO {

    //页号
    private int pageNum = TableSupport.DEFAULT_PAGE_NUM;

    //页大小
    private int pageSize = TableSupport.DEFAULT_PAGE_SIZE;

}
