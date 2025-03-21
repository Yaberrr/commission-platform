package com.moe.platform.convert;

import com.moe.platform.utils.PlatformUtils;
import com.moe.platform.vo.CouponVO;
import com.moe.platform.vo.ProductVO;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsSearchResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 拼多多转换类
 * @author tangyabo
 * @date 2025/3/20
 */
public class PddConvert {

    public static ProductVO toProductVO(PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem item) {
        ProductVO vo = new ProductVO();
        vo.setProductId(item.getGoodsSign());
        vo.setShopName(item.getMallName());
        vo.setProductName(item.getGoodsName());
        vo.setProductImgUrl(item.getGoodsImageUrl());
        vo.setProductThumbnailUrl(item.getGoodsThumbnailUrl());
        vo.setProductDesc(item.getGoodsDesc());
        vo.setPrice(BigDecimal.valueOf(item.getMinGroupPrice()));
        vo.setSoldQuantity(item.getSalesTip());

        //判断最优券
        List<CouponVO> couponVOList = new ArrayList<>();
        if(item.getHasCoupon() && item.getCouponRemainQuantity() > 0){
            CouponVO couponVO = new CouponVO();
            couponVO.setDiscount(BigDecimal.valueOf(item.getCouponDiscount()));
            couponVO.setStartTime(new Date(item.getCouponStartTime()));
            couponVO.setEndTime(new Date(item.getCouponEndTime()));
            couponVOList.add(couponVO);
        }
        if(item.getHasMallCoupon() && item.getMallCouponRemainQuantity() > 0){
            CouponVO couponVO = new CouponVO();
            couponVO.setDiscount(BigDecimal.valueOf(item.getMallCouponMaxDiscountAmount()));
            couponVO.setStartTime(new Date(item.getMallCouponStartTime()));
            couponVO.setEndTime(new Date(item.getMallCouponEndTime()));
            couponVOList.add(couponVO);
        }
        vo.setBestCoupon(PlatformUtils.getBestCoupon(couponVOList));
        //券后价
        if(vo.getBestCoupon() != null) {
            vo.setLowestPrice(vo.getPrice().subtract(vo.getBestCoupon().getDiscount()));
        }else {
            vo.setLowestPrice(vo.getPrice());
        }

        //佣金计算
        BigDecimal rate = BigDecimal.ZERO;
        if(item.getPromotionRate() != null){
            rate = BigDecimal.valueOf(item.getPromotionRate());
        }
        if(item.getActivityPromotionRate() != null && item.getActivityPromotionRate() > rate.longValue()){
            rate = BigDecimal.valueOf(item.getActivityPromotionRate());
        }
        vo.setCommission(vo.getLowestPrice().multiply(rate).divide(new BigDecimal("1000"),2, RoundingMode.DOWN));
        return vo;
    }
}
