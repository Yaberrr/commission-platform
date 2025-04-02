package com.moe.wallet.dto;

import com.moe.common.core.enums.wallet.WalletEventType;
import com.moe.common.core.enums.wallet.WalletFlowType;
import com.moe.common.core.enums.wallet.WalletRecordStatus;
import com.moe.common.core.enums.wallet.WalletRewardLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletRecordDTO {

    // 用户id
    private Long userId;

    // 事件类型
    private WalletEventType eventType;

    // 变动金额
    private BigDecimal amount;

    // 订单id
    private Long orderId;

    // 订单编号
    private String orderNo;

    // 订单金额
    private BigDecimal orderAmount;

    // 返佣级别 1本人 2上游  3上上游
    private WalletRewardLevel rewardLevel;

}

