package com.moe.common.core.domain.dto.tutorial;

import lombok.Data;

@Data
public class TutorialSortDTO {

    private Long id;

    // 1上移 2下移 3顶置
    private Integer type;
}
