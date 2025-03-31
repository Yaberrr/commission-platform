package com.moe.user.controller;

import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.OnlyList;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.security.service.TokenService;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.platform.api.IPlatformAuthApi;
import com.moe.platform.vo.PlatformUrlVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author tangyabo
 * @date 2025/3/12
 */
@Tag(name = "用户")
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private IPlatformAuthApi platformAuthApi;
    @Autowired
    private TokenService tokenService;

    @Operation(description = "用户信息")
    @PostMapping("/info")
    public R<User> getInfo() {
        return R.ok(SecurityUtils.getAppUser());
    }

    @Operation(description = "获取平台授权url")
    @PostMapping("/platformAuthUrl")
    public R<PlatformUrlVO> platformAuthUrl(@Parameter(description = "平台类型")@RequestParam Integer platformType) {
        return platformAuthApi.generateAuthUrl(PlatformType.fromCode(platformType));
    }

    @Operation(description = "查询平台授权列表")
    @PostMapping("/platformAuthList")
    public R<OnlyList<PlatformAuth>> platformAuthList() {
        List<PlatformAuth> authList = platformAuthApi.authList(SecurityUtils.getAppUser().getId()).getData();
        //刷新缓存
        LoginUser loginUser = SecurityUtils.getLoginUser();
        loginUser.setAppAuthList(authList);
        tokenService.refreshToken(loginUser);
        return R.ok(new OnlyList<>(authList));
    }

}
