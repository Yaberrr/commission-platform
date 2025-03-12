package com.moe.common.core.domain.config;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.config.UserConfigType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 用户配置
 */
@Data
@TableName("rb_user_config")
public class UserConfig extends BaseEntity {
    @TableId
    private Long id;

    // 用户id
    private Long userId;

    // 配置类型
    private UserConfigType configType;

    // 配置值
    private String configValue;
}
