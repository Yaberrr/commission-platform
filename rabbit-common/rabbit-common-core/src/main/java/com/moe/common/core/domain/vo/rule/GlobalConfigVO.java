package com.moe.common.core.domain.vo.rule;

import lombok.Data;

@Data
public class GlobalConfigVO {
    private Long id;

    private Integer configType;

    private String configJson;
}
