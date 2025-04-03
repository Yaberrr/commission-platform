package com.moe.wallet.api;

import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.WalletRecord;
import com.moe.common.core.enums.wallet.WalletRecordStatus;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.wallet.dto.WalletRecordListDTO;
import com.moe.wallet.dto.WalletRecordDTO;
import com.moe.wallet.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author tangyabo
 * @date 2025/4/3
 */
@RestController
@RequestMapping("/walletInner")
public class WalletApi implements IWalletApi{

    @Autowired
    private IWalletService walletService;

    @InnerAuth
    @Override
    public R<Boolean> addWalletRecord(WalletRecordDTO dto) {
        walletService.addWalletRecord(dto);
        return R.ok(true);
    }

    @InnerAuth
    @Override
    public R<WalletRecord> changeWalletRecordStatus(@RequestParam Long recordId, @RequestParam WalletRecordStatus status) {
        return R.ok(walletService.changeWalletRecordStatus(recordId, status));
    }

    @Override
    public R<Set<Long>> walletRecordIds(@RequestBody WalletRecordListDTO dto) {
        return R.ok(walletService.walletRecordIds(dto));
    }
}
