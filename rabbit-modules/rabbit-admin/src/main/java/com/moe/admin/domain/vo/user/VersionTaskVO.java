package com.moe.admin.domain.vo.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class VersionTaskVO {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
