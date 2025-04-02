package com.moe.wallet;

import com.moe.common.core.enums.config.PlatformConfigType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.enums.wallet.WalletEventType;
import com.moe.common.core.enums.wallet.WalletRecordStatus;
import com.moe.common.core.enums.wallet.WalletRewardLevel;
import com.moe.common.core.vo.CommissionConfig;
import com.moe.wallet.dto.WalletRecordDTO;
import com.moe.wallet.service.IWalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
@SpringBootTest(classes = MoeWalletApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class WalletTest {

    @Autowired
    private IWalletService walletService;

    @Test
    public void testMyWallet(){
        System.out.println(walletService.myWallet(2L,false));
    }

    @Test
    public void testAddWalletRecord(){
        WalletRecordDTO dto =new WalletRecordDTO();
        dto.setOrderId(1L);
        dto.setOrderNo("test");
        dto.setOrderAmount(BigDecimal.valueOf(100));
        dto.setAmount(BigDecimal.valueOf(50));
        dto.setUserId(9L);
        dto.setEventType(WalletEventType.ORDER_COMMISSION);
        dto.setRewardLevel(WalletRewardLevel.PARENT);
        walletService.addWalletRecord(dto);
    }

    @Test
    public void testChangeStatus(){
        for(int i = 0; i < 20; i++) {
            new Thread(() -> walletService.changeWalletRecordStatus(8L, WalletRecordStatus.INVALID)).start();
        }
    }

    @Test
    public void testConfig(){
//        Object config = publicPlatformConfigService.getConfig(PlatformType.PDD, PlatformConfigType.COMMISSION_RATIO);

//        System.out.println((CommissionConfig)config);
    }
}
