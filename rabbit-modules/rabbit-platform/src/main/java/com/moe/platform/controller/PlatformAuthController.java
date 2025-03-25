package com.moe.platform.controller;

import com.moe.common.core.domain.R;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.platform.api.PlatformAuthApi;
import com.moe.platform.service.PlatformAuthService;
import com.moe.platform.service.PlatformServiceFactory;
import com.moe.platform.vo.AuthUrlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台授权
 * @author tangyabo
 * @date 2025/3/25
 */
@RestController
@RequestMapping("/platformAuth")
public class PlatformAuthController implements PlatformAuthApi {

    @Autowired
    private PlatformServiceFactory platformServiceFactory;

    @InnerAuth
    @Override
    @PostMapping("/createAuth")
    public R<PlatformAuth> createAuth(PlatformType platformType,Long userId) {
        PlatformAuthService authService = platformServiceFactory.getAuthService(platformType);
        return R.ok(authService.createAuth(userId));
    }

    @InnerAuth
    @Override
    @PostMapping("/checkAuth")
    public R<Boolean> checkAuth(PlatformAuth auth) {
        PlatformAuthService authService = platformServiceFactory.getAuthService(auth.getPlatformType());
        return R.ok(authService.checkAuth(auth));
    }

    @InnerAuth
    @Override
    @PostMapping("/generateAuthUrl")
    public R<AuthUrlVO> generateAuthUrl(PlatformAuth auth) {
        PlatformAuthService authService = platformServiceFactory.getAuthService(auth.getPlatformType());
        return R.ok(authService.generateAuthUrl(auth));
    }
}
