package com.moe.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 发布状态
 */
@Getter
public enum PublishStatus {

    UNPUBLISHED(0, "未发布"),
    SCHEDULED(1, "预计发布"),
    PUBLISHED(2, "已发布");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    PublishStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static PublishStatus fromCode(int code) {
        for (PublishStatus e : PublishStatus.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
