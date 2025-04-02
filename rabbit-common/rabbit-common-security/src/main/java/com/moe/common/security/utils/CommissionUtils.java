package com.moe.common.security.utils;

import com.moe.common.core.constant.DecimalConstants;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.vo.CommissionCalculateVO;
import com.moe.common.core.vo.CommissionConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 佣金计算类
 * @author tangyabo
 * @date 2025/4/1
 */
public class CommissionUtils {

    /**
     * 计算佣金
     * @param commissionConfig 佣金配置
     * @param platformCommission 平台总佣金
     * @param level 会员等级
     * @return
     */
    public static CommissionCalculateVO calculate(CommissionConfig commissionConfig, BigDecimal platformCommission, MemberLevel level){
        CommissionConfig.LevelConfig config = commissionConfig.getLevelList().stream().filter(c -> c.getLevel().equals(level))
                .findFirst().orElseThrow(() -> new ServiceException("佣金配置不存在"));
        CommissionCalculateVO result = new CommissionCalculateVO();

        //可分配
        BigDecimal allocatedCommission = platformCommission.multiply(config.getTotalRate()).divide(DecimalConstants.HUNDRED, 2, RoundingMode.DOWN);
        result.setAllocatedCommission(allocatedCommission);
        //上级
        result.setParentCommission(
                allocatedCommission.multiply(config.getParentRate()).divide(DecimalConstants.HUNDRED, 2, RoundingMode.DOWN)
        );
        //上上级
        result.setGrandParentCommission(
                allocatedCommission.multiply(config.getGrandParentRate()).divide(DecimalConstants.HUNDRED, 2, RoundingMode.DOWN)
        );
        //本人佣金
        result.setCommission(
                allocatedCommission.subtract(result.getParentCommission()).subtract(result.getGrandParentCommission())
        );
        return result;
    }
}
