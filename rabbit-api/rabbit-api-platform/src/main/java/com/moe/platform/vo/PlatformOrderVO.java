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

    //授权id
    private String authId;

    //平台编号
    private String platformNo;

    // 订单状态
    private OrderStatus status;

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

    //平台佣金
    private BigDecimal platformCommission;

    // 本人佣金
    private BigDecimal commission;

    // 上级佣金
    private BigDecimal parentCommission;

    // 上上级佣金
    private BigDecimal grandParentCommission;

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

}
