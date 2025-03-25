package com.moe.user.controller;

import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.utils.Assert;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.platform.vo.AuthUrlVO;
import com.moe.user.api.UserApi;
import com.moe.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author tangyabo
 * @date 2025/3/12
 */
@Tag(name = "用户")
@RestController
@RequestMapping
public class UserController implements UserApi {

    @Autowired
    private IUserService userService;

    @InnerAuth
    @Override
    @PostMapping("/saveUser")
    public R<User> saveUser(User user) {
        return R.ok(userService.saveUser(user));
    }

    @Operation(description = "用户信息")
    @PostMapping("/info")
    public R<User> getInfo() {
        return R.ok(SecurityUtils.getAppUser());
    }

    @Operation(description = "获取平台授权url")
    @PostMapping("/platformAuthUrl")
    public R<AuthUrlVO> platformAuthUrl(@Parameter(description = "平台类型")@RequestParam Integer platformType) {
        return R.ok(userService.getPlatformAuthUrl(PlatformType.fromCode(platformType)));
    }



}
