package com.moe.common.core.enums.user;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum VersionTaskType {

    MandatoryUpdate(1,"强制更新"),
    NonMandatoryUpdate(2,"非强制更新"),
    UpdateMaintenanceNotice(3,"更新维护通知"),
    NoUpdatePrompt(4,"不提示更新");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    VersionTaskType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static VersionTaskType fromCode(Integer code){
        if(code == null) return null;
        for(VersionTaskType versionTaskType: VersionTaskType.values()){
            if (versionTaskType.code == code) {
                return versionTaskType;
            }
        }
        return null;
    }
}
