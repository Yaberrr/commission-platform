package com.moe.common.core.domain.vo.user;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MemberShipVO {
    private Long id;

    private Sycee silverSycee;

    private Sycee goldSycee;

    private BigDecimal reward;

    private List<Benefits> benefitsList;

    @Data
    public static class Sycee{
        private Integer timePeriod;
        private Integer count;
        private BigDecimal income;
    }

    @Data
    public static class Benefits{
        private Integer platformType;
        private ProfitRatio copper;
        private ProfitRatio silver;
        private ProfitRatio gold;
    }

    @Data
    public static class ProfitRatio{
        private Integer earns;
        private Integer first;
        private Integer second;
        private Integer third;
    }
}
