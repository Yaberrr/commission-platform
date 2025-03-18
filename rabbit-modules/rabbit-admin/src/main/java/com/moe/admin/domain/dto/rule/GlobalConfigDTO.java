package com.moe.admin.domain.dto.rule;

import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
public class GlobalConfigDTO extends BaseEntity {

    private Long id;

    private Integer configType;

    private String configJson;
}
