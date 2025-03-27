package com.moe.platform.vo;

import lombok.Data;

import java.util.List;

/**
 * 商品详情
 * @author tangyabo
 * @date 2025/3/27
 */
@Data
public class ProductDetailVO extends ProductVO{

    //商品图片列表
    private List<String> imgList;

    //商品视频列表
    private List<String> videoList;

    //商品标签列表
    private List<String> tagList;

    //商品介绍
    private String introduction;

}
