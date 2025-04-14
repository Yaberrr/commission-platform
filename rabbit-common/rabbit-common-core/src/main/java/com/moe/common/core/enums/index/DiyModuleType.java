package com.moe.common.core.enums.index;

import lombok.Getter;

@Getter
public enum DiyModuleType {
    EMPTY(0),
    BANNER(1),
    MULTIPLE_IMAGE(2),
    MULTIPLE_CLICK_IMAGE(3),
    VIDEO(4),
    TITLE(5),
    DOUBLE_PRODUCT_SHOW(6),
    THREE_PRODUCT_SHOW(7),
    SINGLE_PRODUCT_SHOW(8),
    LATERAL_PRODUCT_SHOW(9),
    NEW_WELFARE(10),
    PURCHASE_LIMIT(11),
    COUNT_DOWN(12),
    TAB_BAR(101),
    MULTIPLE_SHOW(13),
    SIDE_BAR(14);
    Integer moduleType;

    private DiyModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public static DiyModuleType diyModelType(int moduleType) {
        for (DiyModuleType type : DiyModuleType.values()) {
            if (type.getModuleType() == moduleType) {
                return type;
            }
        }
        return null;
    }
}
