package com.moe.user.domain.dto;

import com.moe.common.core.enums.config.UserConfigType;
import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/4/10
 */
@Data
public class UserConfigDTO {

    // 配置类型
    private UserConfigType configType;

    // 配置值
    private String configValue;

    public void setConfigType(int code){
        this.configType = UserConfigType.fromCode(code);
    }
}
