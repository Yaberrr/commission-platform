package com.moe.order.service.impl;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.wallet.WalletEventType;
import com.moe.common.core.enums.wallet.WalletRecordStatus;
import com.moe.common.core.enums.wallet.WalletRewardLevel;
import com.moe.order.service.IOrderCommissionService;
import com.moe.wallet.api.IWalletApi;
import com.moe.wallet.dto.WalletRecordDTO;
import com.moe.wallet.dto.WalletRecordListDTO;
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
public class OrderCommissionServiceImpl implements IOrderCommissionService {


    //todo: 都改为mq异步
    @Autowired
    private IWalletApi walletApi;

    @Override
    public void addCommission(Order order) {
        WalletRecordDTO self = new WalletRecordDTO(order);
        self.setRewardLevel(WalletRewardLevel.SELF);
        self.setAmount(order.getCommission());
        self.setUserId(order.getUserId());
        walletApi.addWalletRecord(self);

        if(order.getParentUserId() != null){
            WalletRecordDTO parent = new WalletRecordDTO(order);
            parent.setRewardLevel(WalletRewardLevel.PARENT);
            parent.setAmount(order.getParentCommission());
            parent.setUserId(order.getParentUserId());
            walletApi.addWalletRecord(parent);
        }
        if(order.getGrandParentUserId() != null){
            WalletRecordDTO grandParent = new WalletRecordDTO(order);
            grandParent.setRewardLevel(WalletRewardLevel.GRANDPARENT);
            grandParent.setAmount(order.getGrandParentCommission());
            grandParent.setUserId(order.getGrandParentUserId());
            walletApi.addWalletRecord(grandParent);
        }
    }

    @Override
    public void commissionToAccount(Order order) {
        //查询订单相关记录
        WalletRecordListDTO dto = new WalletRecordListDTO();
        dto.setOrderId(order.getId());
        dto.setWalletEventType(WalletEventType.ORDER_COMMISSION);
        dto.setStatus(WalletRecordStatus.INIT);
        Set<Long> recordIds = walletApi.walletRecordIds(dto).getData();
        for (Long recordId : recordIds) {
            walletApi.changeWalletRecordStatus(recordId,WalletRecordStatus.RECEIVED);
        }
    }

    @Override
    public void deductCommission(Order order){
        //查询订单相关记录
        WalletRecordListDTO dto = new WalletRecordListDTO();
        dto.setOrderId(order.getId());
        dto.setWalletEventType(WalletEventType.ORDER_COMMISSION);
        dto.setStatus(WalletRecordStatus.INIT);
        Set<Long> recordIds = walletApi.walletRecordIds(dto).getData();
        for (Long recordId : recordIds) {
            walletApi.changeWalletRecordStatus(recordId,WalletRecordStatus.INVALID);
        }
    }

}
