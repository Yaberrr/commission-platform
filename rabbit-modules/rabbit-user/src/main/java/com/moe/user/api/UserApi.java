package com.moe.user.api;

import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.User;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author tangyabo
 * @date 2025/3/28
 */
@RestController
@RequestMapping("/userInner")
public class UserApi implements IUserApi {

    @Autowired
    private IUserService userService;

    @InnerAuth
    @Override
    public R<User> saveUser(User user) {
        return R.ok(userService.saveUser(user));
    }

    @InnerAuth
    @Override
    public R<Map<String, Integer>> userMemberLevelMap() {
        return R.ok(userService.getUserMemberLevelMap());
    }

}
