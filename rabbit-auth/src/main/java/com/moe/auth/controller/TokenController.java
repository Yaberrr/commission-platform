package com.moe.auth.controller;

import com.moe.auth.form.LoginBody;
import com.moe.auth.service.AppLoginService;
import com.moe.auth.service.SysLoginService;
import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.R;
import com.moe.common.core.utils.JwtUtils;
import com.moe.common.core.utils.StringUtils;
import com.moe.common.security.auth.AuthUtil;
import com.moe.common.security.service.TokenService;
import com.moe.common.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 后台token控制
 *
 * @author ruoyi
 */
@RestController
public class TokenController
{
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private AppLoginService appLoginService;

    @Operation(description = "管理员登录")
    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form)
    {
        // 用户登录
        LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    /*@Operation(description = "管理员注册")
    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody)
    {
        // 用户注册
        sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        return R.ok();
    }*/

    @Operation(description = "App手机登录")
    @PostMapping("mobileLogin")
    public R<?> mobileLogin(@Parameter(description="手机号")@RequestParam String phoneNumber,
                            @Parameter(description="验证码")@RequestParam String code){
        LoginUser loginUser = appLoginService.mobileLogin(phoneNumber, code);
        return R.ok(tokenService.createToken(loginUser));
    }

    @Operation(description = "App发送手机验证码")
    @PostMapping("sendCode")
    public R<?> sendCode(@Parameter(description="手机号")@RequestParam String phoneNumber){
        return appLoginService.sendCode(phoneNumber);
    }


    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        String accessToken = SecurityUtils.getAccessToken(request);
        if (StringUtils.isNotEmpty(accessToken))
        {
            String username = JwtUtils.getUserName(accessToken);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(accessToken);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }


}
