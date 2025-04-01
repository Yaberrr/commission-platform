package com.moe.wallet.service;

import com.moe.common.core.domain.user.Wallet;
import com.moe.common.core.domain.user.WalletRecord;
import com.moe.common.core.enums.wallet.WalletRecordStatus;
import com.moe.wallet.dto.WalletRecordDTO;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
public interface IWalletService {

    /**
     * 我的钱包
     * @param userId 用户id
     * @param withLock 是否加锁
     * @return
     */
    Wallet myWallet(Long userId, boolean withLock);

    /**
     * 新增钱包记录
     * @param dto
     */
    void addWalletRecord(WalletRecordDTO dto);

    /**
     * 消费钱包记录
     * @param dto
     */
    void consumeWalletRecord(WalletRecordDTO dto);

    /**
     * 修改钱包记录的状态
     */
    WalletRecord changeWalletRecordStatus(Long recordId, WalletRecordStatus status);
}
