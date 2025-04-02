package com.moe.common.core.domain.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.config.PlatformConfigType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 平台配置
 */
@Data
@TableName("rb_platform_config")
public class PlatformConfig extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 平台类型
    private PlatformType platformType;

    // 配置类型 1返佣比例
    private PlatformConfigType configType;

    // 配置内容
    private String configJson;
}
