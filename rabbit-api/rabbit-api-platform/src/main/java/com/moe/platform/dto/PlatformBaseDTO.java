package com.moe.platform.dto;

import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tangyabo
 * @date 2025/3/27
 */
@Data
public class PlatformBaseDTO {

    @NotNull(message = "平台类型不可为空")
    private PlatformType platformType;

    public void setPlatformType(Integer code) {
        this.platformType = PlatformType.fromCode(code);
    }

}
