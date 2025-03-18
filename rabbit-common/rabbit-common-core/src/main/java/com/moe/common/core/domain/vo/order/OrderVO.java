package com.moe.common.core.domain.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderVO {

    private Long id;

    @Excel(name = "用户id")
    private String userId;

    @Excel(name = "订单编号")
    private String orderNo;

    @Excel(name = "订单状态")
    private Integer status;

    @Excel(name = "商品名称")
    private String productName;

    @Excel(name = "商品图片")
    private String productImg;

    @Excel(name = "订单总价")
    private BigDecimal orderAmount;

    @Excel(name = "订单平台")
    private Integer platformType;

    @Excel(name = "平台总佣金")
    private BigDecimal platformCommission;

    @Excel(name = "本人佣金")
    private BigDecimal commission;

    @Excel(name = "上级佣金")
    private BigDecimal parentCommission;

    @Excel(name = "上上级佣金")
    private BigDecimal grandParentCommission;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下单时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;
}
