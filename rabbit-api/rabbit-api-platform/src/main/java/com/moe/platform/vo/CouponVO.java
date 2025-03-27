package com.moe.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    //失效时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    //剩余秒数
    private Long remainSeconds;


}
