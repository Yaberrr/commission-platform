package com.moe.common.core.vo;

import com.moe.common.core.enums.user.MemberLevel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 平台分佣配置
 * @author tangyabo
 * @date 2025/4/1
 */
@Data
public class CommissionConfig {

    private List<LevelConfig> levelList;

    @Data
    public static class LevelConfig{

        //会员等级
        private MemberLevel level;

        //总收益
        private BigDecimal totalRate;

        //一级收益
        private BigDecimal selfRate;

        //二级收益
        private BigDecimal parentRate;

        //三级收益
        private BigDecimal grandParentRate;

    }

}
