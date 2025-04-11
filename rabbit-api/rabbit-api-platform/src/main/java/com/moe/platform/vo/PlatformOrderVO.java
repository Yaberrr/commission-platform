package com.moe.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台订单统一返回
 * @author tangyabo
 * @date 2025/3/31
 */
@Data
public class PlatformOrderVO {

    //认证id
    private String authId;

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

    //下单时间
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

    //失败原因
    private String failReason;

    //是否比价
    private boolean hasPredict;

    //用户id 平台各自不处理，统一处理
    private Long userId;



}
