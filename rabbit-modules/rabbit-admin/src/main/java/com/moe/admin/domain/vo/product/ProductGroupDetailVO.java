package com.moe.admin.domain.vo.product;

import com.moe.common.core.domain.platform.PlatformDict;
import lombok.Data;

import java.util.List;

@Data
public class ProductGroupDetailVO {

    private Long id;

    private String groupName;

    private List<PlatformDict> platformDicts;


}
