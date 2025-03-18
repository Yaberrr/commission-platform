package com.moe.admin.domain.dto.tutorial;

import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
public class TutorialAddDTO extends BaseEntity {

    private Long id;

    private String videoName;

    private String videoCover;

    private String videoUrl;

}
