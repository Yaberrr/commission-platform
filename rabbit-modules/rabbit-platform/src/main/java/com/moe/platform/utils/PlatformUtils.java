package com.moe.platform.utils;

import com.moe.platform.vo.CouponVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 平台公共工具类
 * @author tangyabo
 * @date 2025/3/20
 */
public class PlatformUtils {

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
        return bestCoupon;
    }
}
