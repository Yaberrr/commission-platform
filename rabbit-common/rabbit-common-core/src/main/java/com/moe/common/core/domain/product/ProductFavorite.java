package com.moe.common.core.domain.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品收藏
 *
 * @author liang.lu
 * 2025/4/13 14:19:59
 */
@Data
@TableName(value = "rb_product_favorite", autoResultMap = true)
public class ProductFavorite extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    //用户id
    private Long userId;

    //商品id
    private String productId;

    //平台类型
    private PlatformType platformType;

    //商品名称
    private String productName;

    //商品价格
    private BigDecimal price;

    // 促销价格
    private BigDecimal lowestPrice;

    //优惠券抵扣
    private BigDecimal couponDiscount;

    //预估佣金
    private BigDecimal commission;
}
