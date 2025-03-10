package com.moe.common.core.domain.user;

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
    @TableId
    private Long id;

    // 用户id
    private Long userId;

    // 余额
    private BigDecimal balance;

    // 有效收益
    private BigDecimal totalCommission;

    // 即将到帐收益
    private BigDecimal upcomingCommission;

    // 已提现收益
    private String withdrawnCommission;

    // 有效订单数
    private Integer orderCount;

    // 订单金额
    private BigDecimal orderAmount;

    // 邀请下单数量
    private Integer inviteOrderCount;

    // 下游邀请下单数量
    private Integer childInviteOrderCount;
}
