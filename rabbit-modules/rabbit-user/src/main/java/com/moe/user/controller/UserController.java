package com.moe.user.controller;

import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.OnlyList;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.TokenVO;
import com.moe.common.core.domain.config.UserConfig;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.exception.auth.NotLoginException;
import com.moe.common.security.service.TokenService;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.platform.api.IPlatformAuthApi;
import com.moe.platform.vo.PlatformUrlVO;
import com.moe.user.domain.dto.UserConfigDTO;
import com.moe.user.domain.dto.UserDTO;
import com.moe.user.domain.vo.PlatformAuthVO;
import com.moe.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author tangyabo
 * @date 2025/3/12
 */
@Tag(name = "用户")
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPlatformAuthApi platformAuthApi;
    @Autowired
    private TokenService tokenService;

    @Operation(description = "用户信息")
    @PostMapping("/info")
    public R<User> getInfo() {
        User user = SecurityUtils.getAppUser();
        if(user == null){
            throw new NotLoginException("用户信息不存在");
        }
        return R.ok(user);
    }

    @Operation(description = "修改用户信息")
    @PostMapping("/updateInfo")
    public R<?> updateInfo(UserDTO dto){
        userService.updateInfo(dto);
        return R.ok();
    }

    @Operation(description = "修改用户设置")
    @PostMapping("/updateConfig")
    public R<?> updateConfig(UserConfigDTO dto){
        userService.updateConfig(dto);
        return R.ok();
    }

    @Operation(description = "获取平台授权url")
    @PostMapping("/platformAuthUrl")
    public R<PlatformUrlVO> platformAuthUrl(@Parameter(description = "平台类型")@RequestParam Integer platformType) {
        return platformAuthApi.generateAuthUrl(PlatformType.fromCode(platformType));
    }

    @Operation(description = "查询平台授权列表")
    @PostMapping("/platformAuthList")
    public R<OnlyList<PlatformAuthVO>> platformAuthList() {
        List<PlatformAuth> authList = platformAuthApi.authList(SecurityUtils.getAppUser().getId()).getData();
        //刷新缓存
        LoginUser loginUser = SecurityUtils.getLoginUser();
        loginUser.setAppAuthList(authList);
        tokenService.refreshUserInfo(loginUser);

        Map<PlatformType, PlatformAuthVO> authVOMap = authList.stream().collect(Collectors.toMap(PlatformAuth::getPlatformType,
                p -> new PlatformAuthVO(p.getPlatformType(),p.getStatus())));

        //补全未授权的平台
        for (PlatformType type : PlatformType.values()) {
            authVOMap.computeIfAbsent(type,t -> new PlatformAuthVO(type,0));
        }
        return R.ok(new OnlyList<>(new ArrayList<>(authVOMap.values())));
    }

    @Operation(description = "注销账号")
    @PostMapping("/remove")
    public R<?> remove(){

        return R.ok();
    }

}
