package com.moe.platform.dto.order;

import com.moe.platform.dto.PlatformPageDTO;
import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/3/31
 */
@Data
public class PlatformOrderDTO extends PlatformPageDTO {

    //开始时间
    private long startTime;

    //结束时间
    private long endTime;

}
