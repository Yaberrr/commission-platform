package com.moe.admin.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.enums.index.PublishStatus;
import lombok.Data;

import java.util.Date;

/**
 * @author tangyabo
 * @date 2025/4/11
 */
@Data
public class IndexDiySaveDTO {

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

    // 装修位置 0 首页
    private Integer type;

    //定时发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date schedulePublishTime;

}
