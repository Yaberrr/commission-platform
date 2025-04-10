package com.moe.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单列表vo
 * @author tangyabo
 * @date 2025/4/3
 */
@Data
public class OrderListVO {

    private Long id;

    // 订单编号
    private String orderNo;

    // 订单状态 1已付款 2已收货 3已到账 4已失效
    private OrderStatus status;

    //商品id
    private String productId;

    // 商品名称
    private String productName;

    // 商品图片
    private String productImg;

    // 订单总价
    private BigDecimal orderAmount;

    // 订单平台
    private PlatformType platformType;

    // 佣金
    private BigDecimal commission;

    //下一级佣金
    private BigDecimal nextCommission;

    // 下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    //失效原因
    private String failReason;

    //是否隐藏
    private boolean hasHidden;

    //是否比价
    private boolean hasPredict;

}
