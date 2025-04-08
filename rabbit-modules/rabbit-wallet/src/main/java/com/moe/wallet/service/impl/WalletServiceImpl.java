package com.moe.wallet.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.domain.user.Wallet;
import com.moe.common.core.domain.user.WalletRecord;
import com.moe.common.core.enums.wallet.WalletFlowType;
import com.moe.common.core.enums.wallet.WalletRecordStatus;
import com.moe.common.core.utils.Assert;
import com.moe.common.core.utils.bean.BeanCopyUtils;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.wallet.domain.bo.WalletUpdateBO;
import com.moe.wallet.domain.vo.MyCommissionDetailVO;
import com.moe.wallet.domain.vo.MyCommissionVO;
import com.moe.wallet.dto.WalletRecordListDTO;
import com.moe.wallet.dto.WalletRecordDTO;
import com.moe.wallet.mapper.WalletMapper;
import com.moe.wallet.mapper.WalletRecordMapper;
import com.moe.wallet.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
@Service
public class WalletServiceImpl implements IWalletService {

    @Autowired
    private WalletMapper walletMapper;
    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Override
    public MyCommissionVO myCommission() {
        Long userId = SecurityUtils.getAppUser().getId();
        MyCommissionVO vo = walletMapper.selectMyCommission(userId);
        Wallet wallet = this.myWallet(userId, false);
        vo.setUpcomingAndBalance(wallet.getUpcomingCommission().add(wallet.getBalance()));
        return vo;
    }

    @Override
    public MyCommissionDetailVO myCommissionDetail() {
        Long userId = SecurityUtils.getAppUser().getId();

        MyCommissionDetailVO detailVO = new MyCommissionDetailVO();

        Wallet wallet = this.myWallet(userId, false);
        detailVO.setBalance(wallet.getBalance());
        detailVO.setHistoryCommission(wallet.getBalance().add(wallet.getWithdrawnCommission()));

        //今日
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date today = calendar.getTime();
        detailVO.setTodayData(this.getCommissionUnit(userId, today, new Date()));

        //昨日
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date yesterday = calendar.getTime();
        detailVO.setYesterdayData(this.getCommissionUnit(userId, yesterday, today));

        //本月
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date thisMonth = calendar.getTime();
        detailVO.setThisMonthData(this.getCommissionUnit(userId, thisMonth, new Date()));

        //上月
        calendar.add(Calendar.MONTH, -1);
        Date lastMonth = calendar.getTime();
        detailVO.setLastMonthData(this.getCommissionUnit(userId, lastMonth, thisMonth));
        return detailVO;
    }

    /**
     * 获取收益明细数据
     * @return
     */
    private MyCommissionDetailVO.Unit getCommissionUnit(Long userId, Date startTime, Date endTime) {
        MyCommissionDetailVO.Unit unit = new MyCommissionDetailVO.Unit();
        List<MyCommissionDetailVO.Unit.Row> rowList = walletMapper.selectMyCommissionDetail(userId, DateUtil.beginOfDay(new Date()), new Date());
        unit.setTotalRow(rowList.stream().reduce(new MyCommissionDetailVO.Unit.Row(), (total,row) -> {
            total.setMyOrderCommission(total.getMyOrderCommission().add(row.getMyOrderCommission()));
            total.setOtherOrderCommission(total.getOtherOrderCommission().add(row.getOtherOrderCommission()));
            total.setOrderCount(total.getOrderCount() + row.getOrderCount());
            return total;
        }));
        unit.setRowList(rowList);
        return unit;
    }


    @Override
    public Wallet myWallet(Long userId, boolean needLock) {
        Wallet wallet;
        if(needLock){
            wallet = walletMapper.selectByUserIdForUpdate(userId);
        }else{
            wallet = walletMapper.selectOne(new LambdaQueryWrapper<Wallet>().eq(Wallet::getUserId, userId));
        }
        if(wallet == null){
            //初始化钱包
            wallet = new Wallet();
            wallet.setUserId(userId);
            wallet.setBalance(BigDecimal.ZERO);
            wallet.setTotalCommission(BigDecimal.ZERO);
            wallet.setUpcomingCommission(BigDecimal.ZERO);
            wallet.setWithdrawnCommission(BigDecimal.ZERO);
            wallet.setOrderAmount(BigDecimal.ZERO);
            walletMapper.insert(wallet);
        }
        return wallet;
    }


    @Override
    @Transactional
    public void addWalletRecord(WalletRecordDTO dto) {
        if(dto.getAmount().compareTo(BigDecimal.ZERO) == 0){
            //变动金额为0，不做处理
            return;
        }
        WalletFlowType flowType = dto.getEventType().getFlowType();
        //扣除才加锁
        Wallet wallet = this.myWallet(dto.getUserId(), flowType == WalletFlowType.EXPENSE);
        //判断正负
        BigDecimal amount = flowType == WalletFlowType.EXPENSE? dto.getAmount().abs().negate():dto.getAmount().abs();
        //校验余额
        Assert.isTrue(wallet.getBalance().compareTo(amount.negate()) >= 0,"余额不足");
        WalletRecord record = new WalletRecord();
        BeanCopyUtils.copy(dto,record);
        record.setAmount(amount);
        record.setWalletId(wallet.getId());
        record.setStatus(WalletRecordStatus.INIT);
        record.setFlowType(flowType);
        //唯一约束避免重复消费
        walletRecordMapper.insert(record);

        //修改钱包信息
        WalletUpdateBO updateBO = new WalletUpdateBO();
        updateBO.setWalletId(record.getWalletId());
        switch (dto.getEventType()){
            case ORDER_COMMISSION:
                //下单
                //有效收益 = 历史收益+即将到帐
                //历史收益 = 余额+已提现收益
                updateBO.setTotalCommission(record.getAmount());
                updateBO.setUpcomingCommission(record.getAmount());
                updateBO.setOrderAmount(record.getOrderAmount());
                switch (dto.getRewardLevel()) {
                    case SELF:
                        updateBO.setOrderCount(1);
                        break;
                    case PARENT:
                        updateBO.setInviteOrderCount(1);
                        break;
                    case GRANDPARENT:
                        updateBO.setChildInviteOrderCount(1);
                        break;
                }
                break;
            case WITHDRAW:
                //提现
                //先锁定余额
                updateBO.setBalance(record.getAmount());
                break;
        }
        int count = walletMapper.updateByBO(updateBO);
        Assert.isTrue(count > 0, "更新钱包数据失败");
    }

    @Override
    @Transactional
    public WalletRecord changeWalletRecordStatus(Long recordId, WalletRecordStatus status) {
        Assert.isTrue(status == WalletRecordStatus.RECEIVED || status == WalletRecordStatus.INVALID,
                "仅可修改状态为已失效或已到账");
        //加锁
        WalletRecord record = walletRecordMapper.selectByIdForUpdate(recordId);
        Assert.isTrue(record.getStatus() == WalletRecordStatus.INIT, "已失效或已到账不可再修改");
        record.setStatus(status);
        Assert.isTrue(walletRecordMapper.updateById(record) >0,"更新状态失败");

        WalletUpdateBO updateBO = new WalletUpdateBO();
        updateBO.setWalletId(record.getWalletId());
        switch (record.getEventType()){
            case ORDER_COMMISSION:
                if(status == WalletRecordStatus.RECEIVED){
                    updateBO.setUpcomingCommission(record.getAmount().negate());
                    updateBO.setBalance(record.getAmount());
                } else {
                    updateBO.setTotalCommission(record.getAmount().negate());
                    updateBO.setUpcomingCommission(record.getAmount().negate());
                    updateBO.setOrderAmount(record.getOrderAmount().negate());
                    switch (record.getRewardLevel()) {
                        case SELF:
                            updateBO.setOrderCount(-1);
                            break;
                        case PARENT:
                            updateBO.setInviteOrderCount(-1);
                            break;
                        case GRANDPARENT:
                            updateBO.setChildInviteOrderCount(-1);
                            break;
                    }
                }
                break;
            case WITHDRAW:
                if(status == WalletRecordStatus.RECEIVED){
                    updateBO.setWithdrawnCommission(record.getAmount().negate());
                } else{
                    updateBO.setBalance(record.getAmount().negate());
                }
        }
        Assert.isTrue( walletMapper.updateByBO(updateBO) > 0, "更新钱包数据失败");
        return record;
    }

    @Override
    public Set<Long> walletRecordIds(WalletRecordListDTO dto) {
        LambdaQueryWrapper<WalletRecord> wrapper = new LambdaQueryWrapper<WalletRecord>().select(WalletRecord::getId)
                .eq(WalletRecord::getOrderId, dto.getOrderId())
                        .eq(WalletRecord::getEventType,dto.getWalletEventType())
                                .eq(WalletRecord::getStatus, dto.getStatus());
        List<Long> recordIds =  walletRecordMapper.selectObjs(wrapper);
        return new HashSet<>(recordIds);
    }
}
