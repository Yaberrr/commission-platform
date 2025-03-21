package com.moe.platform.vo;

import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 平台商品统一返回
 * @author tangyabo
 * @date 2025/3/19
 */
@Data
public class ProductVO {

    //商品id
    private String productId;

    //商品名称
    private String productName;

    //商品主图
    private String productImgUrl;

    //商品缩略图
    private String productThumbnailUrl;

    //商品描述
    private String productDesc;

    //商品价格
    private BigDecimal price;

    //促销价
    private BigDecimal lowestPrice;

    //预估佣金
    private BigDecimal commission;

    //已售数量
    private String soldQuantity;

    //最优优惠券
    private CouponVO bestCoupon;

    //店铺名称
    private String shopName;

    //平台类型
    private PlatformType platformType;

}
