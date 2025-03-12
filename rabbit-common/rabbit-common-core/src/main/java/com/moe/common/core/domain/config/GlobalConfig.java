package com.moe.common.core.domain.config;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.config.GlobalConfigType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 通用配置
 */
@Data
@TableName("rb_global_config")
public class GlobalConfig extends BaseEntity {
    @TableId
    private Long id;

    // 配置类型 1客服二维码  2邀请模板 3会员成长
    private GlobalConfigType configType;

    // 配置内容
    private String configJson;
}
