package com.moe.order.domain.dto;

import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tangyabo
 * @date 2025/4/3
 */
@Data
public class OrderListDTO {

    //类型 1我的订单 2其他订单
    @NotNull(message = "订单类型不可为空")
    private Integer type;

    //平台类型
    private PlatformType platformType;

    //订单状态 1已付款 2已收货 3已到账 4已失效
    private OrderStatus status;
}
