package com.moe.common.core.domain.dto.order;

import com.moe.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderListDTO {
    private String orderNo;

    private Integer status;

    private Integer platformType;

    private String orderStartTime;

    private String orderEndTime;

    private BigDecimal minPlatformCommission;

    private BigDecimal maxPlatformCommission;

    private String userId;
}
