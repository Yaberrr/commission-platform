package com.moe.wallet.dto;

import com.moe.common.core.enums.wallet.WalletEventType;
import com.moe.common.core.enums.wallet.WalletRecordStatus;
import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/4/3
 */
@Data
public class WalletRecordListDTO {
    //订单id
    private Long orderId;

    //记录状态
    private WalletRecordStatus status;

    //事件类型
    private WalletEventType walletEventType;
}
