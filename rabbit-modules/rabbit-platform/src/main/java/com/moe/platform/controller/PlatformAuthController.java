package com.moe.platform.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.utils.Assert;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.common.security.service.TokenService;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.platform.api.PlatformAuthApi;
import com.moe.platform.mapper.PlatformAuthMapper;
import com.moe.platform.service.PlatformAuthService;
import com.moe.platform.service.PlatformServiceFactory;
import com.moe.platform.vo.PlatformUrlVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 平台授权
 * @author tangyabo
 * @date 2025/3/25
 */
@RestController
@RequestMapping("/platformAuth")
public class PlatformAuthController implements PlatformAuthApi {

    private static final Logger log = LoggerFactory.getLogger(PlatformAuthController.class);
    @Autowired
    private PlatformServiceFactory platformServiceFactory;
    @Autowired
    private PlatformAuthMapper platformAuthMapper;
    @Autowired
    private TokenService tokenService;


    @InnerAuth
    @Override
    public R<PlatformUrlVO> generateAuthUrl(@RequestParam PlatformType platformType) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        PlatformAuth auth = loginUser.getPlatformAuth(platformType);
        PlatformAuthService authService = platformServiceFactory.getAuthService(platformType);
        if(auth == null){
            //创建授权信息
            auth = authService.createAuth(loginUser.getAppUser().getId());
            platformAuthMapper.insert(auth);
            //刷新缓存
            loginUser.getAppAuthList().add(auth);
            tokenService.refreshToken(loginUser);
        }else if (auth.getStatus() == 0){
            //检查授权状态
            if(authService.checkAuth(auth)){
                auth.setStatus(1);
                platformAuthMapper.updateById(auth);
                //刷新缓存
                tokenService.refreshToken(loginUser);
            }
        }
        Assert.isFalse(auth.getStatus() == 1,"该平台已授权");
        return R.ok(authService.generateAuthUrl(auth));
    }

    @InnerAuth
    @Override
    public R<List<PlatformAuth>> authList(@RequestParam Long userId) {
        List<PlatformAuth> authList = platformAuthMapper.selectList(new LambdaQueryWrapper<PlatformAuth>()
                .eq(PlatformAuth::getUserId, userId));
        for (PlatformAuth auth : authList) {
            //检查授权状态
            if(auth.getStatus() == 0){
                try {
                    PlatformAuthService authService = platformServiceFactory.getAuthService(auth.getPlatformType());
                    if(authService.checkAuth(auth)) {
                        auth.setStatus(1);
                        platformAuthMapper.updateById(auth);
                    }
                }catch (Exception e){
                    //报错不影响接口返回
                    log.error("检查授权状态异常",e);
                }
            }
        }
        return R.ok(authList);
    }

}
