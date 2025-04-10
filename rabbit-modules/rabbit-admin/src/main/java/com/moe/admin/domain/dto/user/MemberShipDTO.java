package com.moe.admin.domain.dto.user;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MemberShipDTO {
    private Long id;

    // 升级条件列表
    private List<MemberUpdateCondition> conditionList;

    // 首次升级奖励金额
    private BigDecimal reward;

    private List<Commission> commissionList;

    // 升级条件
    @Data
    public static class MemberUpdateCondition{
        // 等级
        private Integer level;
        // 时间周期(天)
        private Integer periodDays;
        // 订单数
        private Integer orderCount;
        // 累计收益
        private BigDecimal totalIncome;
    }

    @Data
    public static class Commission {
        // 平台类型
        private Integer platformType;

        private List<Level> levelList;

        @Data
        public static class Level{
            // 等级
            private Integer level;

            // 被分配的总佣金比例
            private Integer totalRate;
            // 自己的佣金比例 （一级)
            private Integer selfRate;
            // 上级佣金比例 (二级)
            private Integer parentRate;
            // 上上级佣金比例 (三级)
            private Integer grandParentRate;
        }
    }
}
