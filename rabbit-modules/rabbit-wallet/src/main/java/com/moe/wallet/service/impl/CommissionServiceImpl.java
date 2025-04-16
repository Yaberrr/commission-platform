package com.moe.wallet.service.impl;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.wallet.WalletEventType;
import com.moe.common.core.enums.wallet.WalletRecordStatus;
import com.moe.common.core.enums.wallet.WalletRewardLevel;
import com.moe.wallet.dto.WalletRecordDTO;
import com.moe.wallet.dto.WalletRecordListDTO;
import com.moe.wallet.service.ICommissionService;
import com.moe.wallet.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author tangyabo
 * @date 2025/4/3
 */
@Service
@Transactional
public class CommissionServiceImpl implements ICommissionService {

    @Autowired
    private IWalletService walletService;

    @Override
    @Transactional
    public void addCommission(Order order) {
        WalletRecordDTO self = new WalletRecordDTO(order);
        self.setRewardLevel(WalletRewardLevel.SELF);
        self.setAmount(order.getCommission());
        self.setUserId(order.getUserId());
        walletService.addWalletRecord(self);

        if(order.getParentUserId() != null){
            WalletRecordDTO parent = new WalletRecordDTO(order);
            parent.setRewardLevel(WalletRewardLevel.PARENT);
            parent.setAmount(order.getParentCommission());
            parent.setUserId(order.getParentUserId());
            walletService.addWalletRecord(parent);
        }
        if(order.getGrandParentUserId() != null){
            WalletRecordDTO grandParent = new WalletRecordDTO(order);
            grandParent.setRewardLevel(WalletRewardLevel.GRANDPARENT);
            grandParent.setAmount(order.getGrandParentCommission());
            grandParent.setUserId(order.getGrandParentUserId());
            walletService.addWalletRecord(grandParent);
        }
    }

    @Override
    @Transactional
    public void commissionToAccount(Order order) {
        //查询订单相关记录
        WalletRecordListDTO dto = new WalletRecordListDTO();
        dto.setOrderId(order.getId());
        dto.setWalletEventType(WalletEventType.ORDER_COMMISSION);
        dto.setStatus(WalletRecordStatus.INIT);
        Set<Long> recordIds = walletService.walletRecordIds(dto);
        for (Long recordId : recordIds) {
            walletService.changeWalletRecordStatus(recordId,WalletRecordStatus.RECEIVED);
        }
    }

    @Override
    @Transactional
    public void deductCommission(Order order){
        //查询订单相关记录
        WalletRecordListDTO dto = new WalletRecordListDTO();
        dto.setOrderId(order.getId());
        dto.setWalletEventType(WalletEventType.ORDER_COMMISSION);
        dto.setStatus(WalletRecordStatus.INIT);
        Set<Long> recordIds = walletService.walletRecordIds(dto);
        for (Long recordId : recordIds) {
            walletService.changeWalletRecordStatus(recordId,WalletRecordStatus.INVALID);
        }
    }

}
