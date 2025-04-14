package com.moe.user.domain.vo;

import com.moe.common.core.enums.platform.PlatformType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/4/10
 */
@Data
@AllArgsConstructor
public class PlatformAuthVO {

    //平台类型
    private PlatformType platformType;

    //状态 0未授权 1已授权
    private Integer status;

}
