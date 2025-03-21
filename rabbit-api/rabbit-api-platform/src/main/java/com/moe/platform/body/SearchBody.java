package com.moe.platform.body;

import lombok.Data;

import java.util.List;

/**
 * 搜索body
 * @author tangyabo
 * @date 2025/3/19
 */
@Data
public class SearchBody {

    //页号
    private int pageNo = 1;

    //页大小
    private int pageSize = 10;

    //排序字段
    private String sortName;

    //排序顺序 asc/desc
    private String sort;

    //关键词
    private String keyword;

    //平台私有参数： 必传，用于指定查询平台
    private List<SearchParam> paramList;

}
