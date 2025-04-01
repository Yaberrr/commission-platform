package com.moe.common.core.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 钱包
 */
@Data
@TableName("rb_wallet")
public class Wallet extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户id
    private Long userId;

    // 有效收益 = 即将到账收益 + 余额 + 已提现收益
    private BigDecimal totalCommission;

    // 即将到帐收益
    private BigDecimal upcomingCommission;

    // 已提现收益
    private BigDecimal withdrawnCommission;

    // 余额
    private BigDecimal balance;


    // 订单金额
    private BigDecimal orderAmount;

    // 有效订单数
    private int orderCount;

    // 邀请下单数量
    private int inviteOrderCount;

    // 下游邀请下单数量
    private int childInviteOrderCount;
}
