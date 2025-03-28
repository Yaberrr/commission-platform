package com.moe.platform.convert;

import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.utils.bean.BeanCopyUtils;
import com.moe.platform.domain.vo.PddGoodsListItemVO;
import com.moe.platform.utils.PlatformUtils;
import com.moe.platform.vo.PlatformUrlVO;
import com.moe.platform.vo.CouponVO;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsDetailResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkRpPromUrlGenerateResponse;

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

    public final static BigDecimal HUNDRED = BigDecimal.valueOf(100);


    public static ProductVO toProductVO(PddGoodsListItemVO item) {
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
        if(item.getHasCoupon() != null && item.getHasCoupon() && item.getCouponRemainQuantity() > 0){
            CouponVO couponVO = new CouponVO();
            couponVO.setDiscount(BigDecimal.valueOf(item.getCouponDiscount()));
            couponVO.setStartTime(new Date(item.getCouponStartTime()*1000));
            couponVO.setEndTime(new Date(item.getCouponEndTime()*1000));
            couponVOList.add(couponVO);
        }
        if(item.getHasMallCoupon() != null && item.getHasMallCoupon() && item.getMallCouponRemainQuantity() > 0){
            CouponVO couponVO = new CouponVO();
            couponVO.setDiscount(BigDecimal.valueOf(item.getMallCouponMaxDiscountAmount()));
            couponVO.setStartTime(new Date(item.getMallCouponStartTime()*1000));
            couponVO.setEndTime(new Date(item.getMallCouponEndTime()*1000));
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
        if(item.getPredictPromotionRate() != null){
            //优先使用比价预判佣金
            rate = BigDecimal.valueOf(item.getPredictPromotionRate());
        }else{
            if(item.getPromotionRate() != null){
                rate = BigDecimal.valueOf(item.getPromotionRate());
            }
            if(item.getActivityPromotionRate() != null && item.getActivityPromotionRate() > rate.longValue()){
                rate = BigDecimal.valueOf(item.getActivityPromotionRate());
            }
        }
        vo.setCommission(vo.getLowestPrice().multiply(rate).divide(new BigDecimal("1000"),2, RoundingMode.DOWN));
        vo.setPlatformType(PlatformType.PDD);

        //分转为元
        vo.setPrice(vo.getPrice().divide(HUNDRED,2,RoundingMode.DOWN));
        vo.setLowestPrice(vo.getLowestPrice().divide(HUNDRED,2,RoundingMode.DOWN));
        vo.setCommission(vo.getCommission().divide(HUNDRED,2,RoundingMode.DOWN));
        if(vo.getBestCoupon() != null){
            vo.getBestCoupon().setDiscount(vo.getBestCoupon().getDiscount().divide(HUNDRED,2,RoundingMode.DOWN));
        }

        //搜索id，用于商品详情及生成链接时回传，提高收益
        vo.setSearchParam(item.getSearchId());
        return vo;
    }


    public static ProductDetailVO toProductDetailVO(PddDdkGoodsDetailResponse.GoodsDetailResponseGoodsDetailsItem detailItem){
        //先提取productVo的相同字段
        PddGoodsListItemVO packVO = BeanCopyUtils.copy(detailItem, PddGoodsListItemVO.class);
        ProductVO productVO = toProductVO(packVO);

        //处理详情独有字段
        ProductDetailVO detailVO = BeanCopyUtils.copy(productVO, ProductDetailVO.class);
        detailVO.setImgList(detailItem.getGoodsGalleryUrls());
        detailVO.setVideoList(detailItem.getVideoUrls());
        detailVO.setIntroduction(detailItem.getGoodsDesc());
        detailVO.setTagList(detailItem.getUnifiedTags());
        return detailVO;
    }

}
