package com.moe.order.dto;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/3
 */
@Data
public class BatchUpdateOrderDTO {

    @NotNull(message = "平台类型不可为空")
    private PlatformType platformType;

    @NotEmpty(message = "订单列表不可为空")
    private List<Order> orderList;
}
