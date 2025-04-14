package com.moe.common.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.enums.index.PublishStatus;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;


/**
 * 首页装修
 */
@Data
@TableName("rb_index_diy")
public class IndexDiy extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    // diy名称
    private String diyName;

    // 发布状态 0 未发布 1 预计发布 2 已发布
    private PublishStatus publishStatus;

    // 起始版本号
    private String minVersion;

    // 结束版本号
    private String maxVersion;

    // 针对平台: H5/IOS/ANDROID,多个逗号分隔
    private String platform;

    // diy内容（json格式）
    private String configContent;

    // 最后保存时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastSaveTime;

    // 最后发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastPublishTime;

    // 预计发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date schedulePublishTime;

    // 装修位置 0 首页
    private Integer type;

}
