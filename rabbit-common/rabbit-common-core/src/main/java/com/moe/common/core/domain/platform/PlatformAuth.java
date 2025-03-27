package com.moe.common.core.domain.platform;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 平台授权
 * @author tangyabo
 * @date 2025/3/24
 */
@Data
@TableName("rb_platform_auth")
public class PlatformAuth extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    //用户id
    private Long userId;

    //授权id
    private String authId;

    //平台类型
    private PlatformType platformType;

    //状态 0未授权 1已授权
    private Integer status;

}
