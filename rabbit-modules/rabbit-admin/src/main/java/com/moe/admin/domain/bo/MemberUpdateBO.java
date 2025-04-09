package com.moe.admin.domain.bo;

import com.moe.admin.domain.dto.user.MemberShipDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员升级条件
 *
 * @author liang.lu
 * 2025/4/9 17:02:52
 */
@Data
public class MemberUpdateBO {
    // 升级条件列表
    private List<MemberShipDTO.MemberUpdateCondition> conditionList;

    // 首次升级奖励金额
    private BigDecimal reward;
}
