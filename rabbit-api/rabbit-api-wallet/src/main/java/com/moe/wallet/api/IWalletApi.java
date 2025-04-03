package com.moe.wallet.api;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.WalletRecord;
import com.moe.common.core.enums.wallet.WalletRecordStatus;
import com.moe.wallet.dto.WalletRecordListDTO;
import com.moe.wallet.dto.WalletRecordDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author tangyabo
 * @date 2025/4/3
 */
@FeignResponseCheck(serviceName = "钱包")
@FeignClient(path = "/walletInner", contextId = "walletApi", value = ServiceNameConstants.WALLET_SERVICE)
public interface IWalletApi {

    /**
     * 新增钱包记录
     * @param dto
     */
    @PostMapping("/addWalletRecord")
    R<Boolean> addWalletRecord(@RequestBody WalletRecordDTO dto);

    /**
     * 修改钱包记录的状态
     */
    @PostMapping("/changeWalletRecordStatus")
    R<WalletRecord> changeWalletRecordStatus(@RequestParam("recordId") Long recordId, @RequestParam("status") WalletRecordStatus status);

    /**
     * 查询订单相关记录id
     */
    @PostMapping("/walletRecordIds")
    R<Set<Long>> walletRecordIds(@RequestBody WalletRecordListDTO dto);

}
