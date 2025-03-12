package com.moe.auth.controller;

import javax.servlet.http.HttpServletRequest;

import com.moe.auth.service.AppLoginService;
import com.moe.auth.service.SysLoginService;
import com.moe.common.core.domain.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.moe.auth.form.LoginBody;
import com.moe.common.core.domain.R;
import com.moe.common.core.utils.JwtUtils;
import com.moe.common.core.utils.StringUtils;
import com.moe.common.security.auth.AuthUtil;
import com.moe.common.security.service.TokenService;
import com.moe.common.security.utils.SecurityUtils;


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
    public R<?> mobileLogin(@Schema(name="手机号") String phoneNumber,
                            @Schema(name="验证码") String code){
        appLoginService.mobileLogin(phoneNumber,code);
        return R.ok();
    }

    @Operation(description = "App发送手机验证码")
    @PostMapping("sendCode")
    public R<?> sendCode(@Schema(name="手机号") String phoneNumber){
        appLoginService.sendCode(phoneNumber);
        return R.ok();
    }


    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
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
