package com.moe.admin.domain.dto.order;

import com.moe.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderListDTO {
    private String orderNo;

    private List<Integer> status;

    private List<Integer> platformType;

    private String orderStartTime;

    private String orderEndTime;

    private BigDecimal minPlatformCommission;

    private BigDecimal maxPlatformCommission;

    private String userId;
}
