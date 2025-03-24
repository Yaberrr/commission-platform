package com.moe.common.core.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
@TableName("rb_version_task")
public class VersionTask extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    //任务名称
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
