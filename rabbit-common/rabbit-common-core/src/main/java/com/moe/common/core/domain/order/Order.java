package com.moe.common.core.domain.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 */
@Data
@TableName("rb_order")
public class Order extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户id
    private Long userId;

    // 订单编号
    private String orderNo;

    //平台编号
    private String platformOrderId;

    // 订单状态
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

    //佣金比例
    private BigDecimal commissionRate;

    //平台总佣金
    private BigDecimal platformCommission;

    //可分配佣金
    private BigDecimal allocatedCommission;

    // 本人佣金
    private BigDecimal commission;

    //上级佣金
    private BigDecimal parentCommission;

    //上级用户id
    private Long parentUserId;

    // 上上级佣金
    private BigDecimal grandParentCommission;

    //上上级用户id
    private Long grandParentUserId;

    // 下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    //支付时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    //确认收货时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    //结算时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleTime;
}
