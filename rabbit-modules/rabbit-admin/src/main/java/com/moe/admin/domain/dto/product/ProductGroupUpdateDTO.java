package com.moe.admin.domain.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductGroupUpdateDTO {

    private Long id;

    private String groupName;

    private List<Long> platformDictIds;
}
