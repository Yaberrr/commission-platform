package com.moe.platform.dto;

import lombok.Data;

import java.util.List;

/**
 * 商品分页查询dto
 * @author tangyabo
 * @date 2025/3/19
 */
@Data
public class PlatformProductDTO {

    //页号
    private int pageNum = 1;

    //页大小
    private int pageSize = 10;

    //排序字段
    private String sortName;

    //排序顺序 asc/desc
    private String sort;

    //平台私有参数： 必传，用于指定查询平台
    private List<PlatformParam> paramList;

}
