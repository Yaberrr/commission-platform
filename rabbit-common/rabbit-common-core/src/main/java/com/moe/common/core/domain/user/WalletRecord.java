package com.moe.common.core.domain.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.user.WalletFlowType;
import com.moe.common.core.enums.user.WalletRecordStatus;
import com.moe.common.core.enums.user.WalletRewardLevel;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 钱包流水
 */
@Data
@TableName("rb_wallet_record")
public class WalletRecord extends BaseEntity {
    @TableId
    private Long id;

    // 用户id
    private Long userId;

    // 钱包id
    private Long walletId;

    // 状态 1未到账 2已失效 3已到账
    private WalletRecordStatus status;

    // 收支类型 0收入  1支出
    private WalletFlowType flowType;

    // 事件类型
    private Integer eventType;

    // 变动金额
    private BigDecimal amount;

    // 订单id
    private Long orderId;

    // 订单编号
    private String orderNumber;

    // 订单金额
    private BigDecimal orderAmount;

    // 返佣级别 1本人 2上游  3上上游
    private WalletRewardLevel rewardLevel;
}
