package com.moe.admin.domain.dto.user;

import lombok.Data;

@Data
public class VersionTaskDTO {

    private Long id;

    private String name;

    //更新任务类型
    private Integer type;

    //安卓最新版本
    private String lastAndroidVersion;

    //安卓强制更新版本
    private String mandatoryAndroidVersion;

    //ios最新版本
    private String lastIosVersion;

    //ios强制更新版本
    private String mandatoryIosVersion;

    //更新内容
    private String content;
}
