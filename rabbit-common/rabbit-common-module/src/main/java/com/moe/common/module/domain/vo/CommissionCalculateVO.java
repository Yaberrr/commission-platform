package com.moe.common.module.domain.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 佣金计算
 * @author tangyabo
 * @date 2025/4/1
 */
@Data
public class CommissionCalculateVO {

    //可分配佣金
    private BigDecimal allocatedCommission;

    //本人佣金
    private BigDecimal commission;

    //上级佣金
    private BigDecimal parentCommission;

    //上上级佣金
    private BigDecimal grandParentCommission;

}
