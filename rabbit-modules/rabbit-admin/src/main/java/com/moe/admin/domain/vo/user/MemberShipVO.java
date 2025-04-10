package com.moe.admin.domain.vo.user;

import com.moe.admin.domain.bo.CommissionBO;
import com.moe.admin.domain.bo.MemberUpdateBO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MemberShipVO {
    private MemberUpdateBO levelUpdate;

    private List<CommissionBO> commissionList;
}
