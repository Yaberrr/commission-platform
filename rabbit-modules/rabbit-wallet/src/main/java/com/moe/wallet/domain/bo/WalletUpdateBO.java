package com.moe.wallet.domain.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
@Data
public class WalletUpdateBO {

    //钱包id
    private Long walletId;

    // 余额
    private BigDecimal balance;

    // 有效收益
    private BigDecimal totalCommission;

    // 即将到帐收益
    private BigDecimal upcomingCommission;

    // 已提现收益
    private BigDecimal withdrawnCommission;

    // 订单金额
    private BigDecimal orderAmount;

    // 有效订单数
    private int orderCount;

    // 邀请下单数量
    private int inviteOrderCount;

    // 下游邀请下单数量
    private int childInviteOrderCount;

}
