package com.moe.wallet.controller;

import com.moe.common.core.domain.R;
import com.moe.wallet.domain.vo.MyCommissionVO;
import com.moe.wallet.service.IWalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
@Tag(name = "钱包")
@RestController
@RequestMapping
public class WalletController {

    @Autowired
    private IWalletService walletService;

    @Operation(description = "我的收益")
    @PostMapping("/myCommission")
    public R<MyCommissionVO> myCommission(){
        return R.ok(walletService.myCommission());
    }

}
