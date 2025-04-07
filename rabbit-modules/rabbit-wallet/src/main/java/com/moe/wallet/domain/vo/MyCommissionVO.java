package com.moe.wallet.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 我的收益vo
 * @author tangyabo
 * @date 2025/4/7
 */
@Data
public class MyCommissionVO {

    //余额  即将到帐+可提现
    private BigDecimal upcomingAndBalance;

    //今日收益
    private BigDecimal todayCommission;

    //昨日收益
    private BigDecimal yesterdayCommission;

    //本月收益
    private BigDecimal thisMonthCommission;

    //上月收益
    private BigDecimal lastMonthCommission;

}
