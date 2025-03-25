package com.moe.admin.domain.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductGroupDTO {

    private String groupName;

    private List<Integer> platformTypes;
}
