package com.moe.common.core.domain.config;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;
import java.util.Date;

/**
 * 平台配置
 */
@Data
@TableName("rb_platform_config")
public class PlatformConfig extends BaseEntity {
    @TableId
    private Long id;

    // 平台类型
    private Integer platformType;

    // 配置类型 1返佣比例
    private Integer configType;

    // 配置内容
    private String configJson;
}
