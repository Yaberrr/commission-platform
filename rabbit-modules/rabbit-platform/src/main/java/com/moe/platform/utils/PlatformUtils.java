package com.moe.platform.utils;

import com.moe.common.core.constant.HttpStatus;
import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.security.service.TokenService;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.platform.dto.PlatformBaseDTO;
import com.moe.platform.dto.PlatformDTO;
import com.moe.platform.mapper.PlatformAuthMapper;
import com.moe.platform.service.IPlatformAuthService;
import com.moe.platform.vo.CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 平台公共工具类
 * @author tangyabo
 * @date 2025/3/20
 */
@Component
public class PlatformUtils {

    @Autowired
    private PlatformAuthMapper platformAuthMapper;
    @Autowired
    private TokenService tokenService;

    /***
     * 获取平台授权信息(可能为空)
     * @param platformType 平台类型
     * @param platformAuthService 对应平台的service
     * @return
     */
    public PlatformAuth getPlatformAuth(PlatformType platformType, IPlatformAuthService platformAuthService){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser != null){
            PlatformAuth auth = loginUser.getPlatformAuth(platformType);
            if(auth != null){
                if(auth.getStatus() == 1){
                    //已授权
                    return auth;
                }
                //未授权则检查授权状态
                if(platformAuthService.checkAuth(auth)){
                    auth.setStatus(1);
                    platformAuthMapper.updateById(auth);
                    //刷新缓存
                    tokenService.refreshToken(loginUser);
                    return auth;
                }
            }
        }
        return null;
    }

    /**
     * 检查平台授权信息(为空则抛出异常)
     * @param platformType
     * @param platformAuthService
     * @return
     */
    public PlatformAuth checkPlatformAuth(PlatformType platformType, IPlatformAuthService platformAuthService){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser == null){
            throw new ServiceException("用户未登录", HttpStatus.UNAUTHORIZED);
        }
        PlatformAuth auth = this.getPlatformAuth(platformType, platformAuthService);
        if(auth == null) {
            throw new ServiceException("平台未授权", HttpStatus.PLATFORM_UNAUTHORIZED, new PlatformDTO(platformType.getCode()));
        }
        return auth;
    }



    /**
     * 获取最优优惠券
     * @param couponVOList
     * @return
     */
    public static CouponVO getBestCoupon(List<CouponVO> couponVOList){
        CouponVO bestCoupon = null;

        for (CouponVO coupon : couponVOList) {
            if(coupon.getStartTime()!=null && coupon.getStartTime().after(new Date())){
                continue;
            }
            if(coupon.getEndTime()!= null && coupon.getEndTime().before(new Date())){
                continue;
            }
            if(bestCoupon == null || coupon.getDiscount().compareTo(bestCoupon.getDiscount())>0){
                bestCoupon = coupon;
            }
        }
        if(bestCoupon != null) {
            bestCoupon.setCouponName("元宝宝专属优惠券");
            if(bestCoupon.getEndTime() != null){
                bestCoupon.setRemainSeconds((bestCoupon.getEndTime().getTime()-System.currentTimeMillis())/1000);
            }
        }
        return bestCoupon;
    }
}
