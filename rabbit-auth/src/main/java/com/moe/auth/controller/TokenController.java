package com.moe.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.moe.auth.body.LoginBody;
import com.moe.auth.service.AppLoginService;
import com.moe.auth.service.SysLoginService;
import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.TokenVO;
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
 * token
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

    @Operation(description = "后台管理员登录")
    @PostMapping("login")
    public R<TokenVO> login(@RequestBody LoginBody form)
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
    @Operation(description = "App发送手机验证码")
    @PostMapping("sendCode")
    public R<?> sendCode(@Parameter(description="手机号")@RequestParam String phoneNumber){
        return appLoginService.sendCode(phoneNumber);
    }

    @Operation(description = "App手机登录")
    @PostMapping("mobileLogin")
    public R<TokenVO> mobileLogin(@Parameter(description="手机号")@RequestParam String phoneNumber,
                            @Parameter(description="验证码")@RequestParam String code){
        LoginUser loginUser = appLoginService.mobileLogin(phoneNumber, code);
        return R.ok(tokenService.createToken(loginUser));
    }

    @Operation(description = "App一键登录")
    @PostMapping("quickLogin")
    public R<TokenVO> quickLogin(@Parameter(description="极光token")@RequestParam String loginToken){
        LoginUser loginUser = appLoginService.quickLogin(loginToken);
        return R.ok(tokenService.createToken(loginUser));
    }

    @Operation(description = "App微信登录")
    @PostMapping("wechatLogin")
    public R<TokenVO> wechatLogin(@Parameter(description = "授权票据")@RequestParam String code){
        LoginUser loginUser = appLoginService.wechatLogin(code);
        return R.ok(tokenService.createToken(loginUser));
    }

    @Operation(description = "登出")
    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        String accessToken = SecurityUtils.getAccessToken(request);
        if (StringUtils.isNotEmpty(accessToken))
        {
//            String username = JwtUtils.getUserName(accessToken);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(accessToken);
          /*  // 记录用户退出日志
            sysLoginService.logout(username);*/
        }
        return R.ok();
    }

    @Operation(description = "刷新token")
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
