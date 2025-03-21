package com.moe.common.core.domain.platform;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.platform.PlatformDictType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 平台字典
 * @author tangyabo
 * @date 2025/3/19
 */
@Data
@TableName("rb_platform_dict")
public class PlatformDict extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    //平台类型
    private PlatformType platformType;

    //字典类型
    private PlatformDictType platformDictType;

    //字典标签
    private String dictText;

    //字典值
    private String dictValue;

    //父级id
    private Long parentId;

    //是否有子级
    private Integer hasChild;

}
