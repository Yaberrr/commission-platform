package com.moe.common.core.enums.product;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ProductGroupDictType {

    ALL(0, "全部"),
    CHANNEL(1, "频道"),
    CATEGORY(2, "分类"),
    TAG(4,"标签");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    ProductGroupDictType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static ProductGroupDictType fromCode(Integer code) {
        if(code == null) return null;
        for (ProductGroupDictType e : ProductGroupDictType.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
