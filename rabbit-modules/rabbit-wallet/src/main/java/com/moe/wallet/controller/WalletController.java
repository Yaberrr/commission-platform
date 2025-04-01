package com.moe.wallet.controller;

import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.domain.user.Wallet;
import com.moe.common.security.utils.SecurityUtils;
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

    @Operation(description = "我的钱包")
    @PostMapping("/myWallet")
    public R<Wallet> myWallet(){
        User user = SecurityUtils.getAppUser();
        return R.ok(walletService.myWallet(user.getId(),false));
    }

}
