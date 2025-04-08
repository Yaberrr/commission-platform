package com.moe.wallet.domain.vo;

import com.moe.common.core.enums.platform.PlatformType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 我的收益明细
 * @author tangyabo
 * @date 2025/4/8
 */
@Data
public class MyCommissionDetailVO {

    //历史收益  可提现+已提现
    private BigDecimal historyCommission;

    //可提现余额
    private BigDecimal balance;

    //今日
    private Unit todayData;

    //昨日
    private Unit yesterdayData;

    //本月
    private Unit thisMonthData;

    //上月
    private Unit lastMonthData;

    @Data
    public static class Unit{

        //汇总
        private Row totalRow;

        //平台数据
        private List<Row> rowList;

        @Data
        public static class Row{

            //来源平台
            private PlatformType platformType;

            //订单笔数
            private int orderCount;

            //我的订单收益
            private BigDecimal myOrderCommission = BigDecimal.ZERO;

            //其他订单收益
            private BigDecimal otherOrderCommission = BigDecimal.ZERO;
        }
    }


}
