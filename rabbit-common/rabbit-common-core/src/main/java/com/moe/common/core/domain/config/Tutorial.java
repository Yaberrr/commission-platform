package com.moe.common.core.domain.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 新手教程
 */
@Data
@TableName("rb_tutorial")
public class Tutorial extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 视频名称
    private String videoName;

    // 视频地址
    private String videoUrl;

    // 视频封面
    private String videoCover;

    // 播放量
    private Integer viewCount;
}
