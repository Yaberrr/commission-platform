package com.moe.platform.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 平台优惠券
 * @author tangyabo
 * @date 2025/3/20
 */
@Data
public class CouponVO {

    //优惠券名称
    private String couponName;

    //抵扣金额
    private BigDecimal discount;

    //生效时间
    private Long startTime;

    //失效时间
    private Long endTime;

    //剩余秒数
    private Long remainSeconds;


}
