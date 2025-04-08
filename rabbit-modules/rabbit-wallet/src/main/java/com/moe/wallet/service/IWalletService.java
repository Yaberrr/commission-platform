package com.moe.wallet.service;

import com.moe.common.core.domain.user.Wallet;
import com.moe.common.core.domain.user.WalletRecord;
import com.moe.common.core.enums.wallet.WalletRecordStatus;
import com.moe.wallet.domain.vo.MyCommissionDetailVO;
import com.moe.wallet.domain.vo.MyCommissionVO;
import com.moe.wallet.dto.WalletRecordListDTO;
import com.moe.wallet.dto.WalletRecordDTO;

import java.util.Set;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
public interface IWalletService {

    /**
     * 我的收益
     * @param
     * @return
     */
    MyCommissionVO myCommission();

    /**
     * 我的收益明细
     * @return
     */
    MyCommissionDetailVO myCommissionDetail();

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
     * 修改钱包记录的状态
     */
    WalletRecord changeWalletRecordStatus(Long recordId, WalletRecordStatus status);

    /**
     * 查询钱包记录ids
     */
    Set<Long> walletRecordIds(WalletRecordListDTO dto);

}
