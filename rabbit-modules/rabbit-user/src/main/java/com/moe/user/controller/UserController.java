package com.moe.user.controller;

import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.User;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.user.api.RemoteUserService;
import com.moe.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangyabo
 * @date 2025/3/12
 */
@RestController
@RequestMapping
public class UserController implements RemoteUserService {

    @Autowired
    private IUserService userService;


    @GetMapping("/info")
    public R<User> getInfo() {
        return R.ok(SecurityUtils.getAppUser());
    }

    @InnerAuth
    @Override
    @PostMapping("/saveUser")
    public R<User> saveUser(User user) {
        return R.ok(userService.saveUser(user));
    }
}
