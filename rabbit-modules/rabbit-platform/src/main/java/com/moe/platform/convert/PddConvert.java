package com.moe.platform.convert;

import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.utils.bean.BeanCopyUtils;
import com.moe.platform.domain.bo.PddGoodsListItemBO;
import com.moe.platform.utils.PlatformUtils;
import com.moe.platform.vo.CouponVO;
import com.moe.platform.vo.PlatformOrderVO;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsDetailResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkOrderListIncrementGetResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.moe.common.core.constant.DecimalConstants.*;

/**
 * 拼多多转换类
 * @author tangyabo
 * @date 2025/3/20
 */
public class PddConvert {



    public static ProductVO toProductVO(PddGoodsListItemBO item) {
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
            couponVO.setStartTime(item.getCouponStartTime());
            couponVO.setEndTime(item.getCouponEndTime());
            couponVOList.add(couponVO);
        }
        if(item.getHasMallCoupon() != null && item.getHasMallCoupon() && item.getMallCouponRemainQuantity() > 0){
            CouponVO couponVO = new CouponVO();
            couponVO.setDiscount(BigDecimal.valueOf(item.getMallCouponMaxDiscountAmount()));
            couponVO.setStartTime(item.getMallCouponStartTime());
            couponVO.setEndTime(item.getMallCouponEndTime());
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
        vo.setCommission(vo.getLowestPrice().multiply(rate).divide(THOUSAND,2, RoundingMode.DOWN));
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

        //判断是否比价
        if(vo.getCommission().compareTo(BigDecimal.ZERO) == 0){
            vo.setHasPredict(true);
        }
        return vo;
    }


    public static ProductDetailVO toProductDetailVO(PddDdkGoodsDetailResponse.GoodsDetailResponseGoodsDetailsItem detailItem){
        //先提取productVo的相同字段
        PddGoodsListItemBO packVO = BeanCopyUtils.copy(detailItem, PddGoodsListItemBO.class);
        ProductVO productVO = toProductVO(packVO);

        //处理详情独有字段
        ProductDetailVO detailVO = BeanCopyUtils.copy(productVO, ProductDetailVO.class);
        detailVO.setImgList(detailItem.getGoodsGalleryUrls());
        detailVO.setVideoList(detailItem.getVideoUrls());
        detailVO.setIntroduction(detailItem.getGoodsDesc());
        detailVO.setTagList(detailItem.getUnifiedTags());
        return detailVO;
    }

    public static PlatformOrderVO toOrderVO(PddDdkOrderListIncrementGetResponse.OrderListGetResponseOrderListItem item) {
        PlatformOrderVO orderVO = new PlatformOrderVO();
        orderVO.setAuthId(item.getPId());
        orderVO.setPlatformOrderId(item.getOrderId());
        orderVO.setOrderNo(item.getOrderSn());
        orderVO.setProductId(item.getGoodsSign());
        orderVO.setProductName(item.getGoodsName());
        orderVO.setProductImg(item.getGoodsThumbnailUrl());
        orderVO.setCommissionRate(BigDecimal.valueOf(item.getPromotionRate()).divide(TEN,2,RoundingMode.DOWN));
        orderVO.setPlatformCommission(BigDecimal.valueOf(item.getPromotionAmount()).divide(HUNDRED,2,RoundingMode.DOWN));
        orderVO.setOrderAmount(BigDecimal.valueOf(item.getOrderAmount()).divide(HUNDRED,2,RoundingMode.DOWN));
        orderVO.setOrderTime(new Date(item.getOrderCreateTime()*1000));
        orderVO.setPayTime(new Date(item.getOrderPayTime()*1000));
        orderVO.setFailReason(item.getFailReason());
        orderVO.setHasPredict(item.getPriceCompareStatus() == 1);
        if(item.getOrderReceiveTime() != null) {
            orderVO.setReceiveTime(new Date(item.getOrderReceiveTime()*1000));
        }
        if(item.getOrderSettleTime() != null) {
            orderVO.setSettleTime(new Date(item.getOrderSettleTime()*1000));
        }
        switch (item.getOrderStatus()){
            case 0://已支付
            case 1://已成团
                orderVO.setStatus(OrderStatus.PAID);
                break;
            case 2://确认收货
            case 3://审核成功
                orderVO.setStatus(OrderStatus.RECEIVED);
                break;
            case 5://已经结算
                orderVO.setStatus(OrderStatus.ACCOUNTED);
                break;
            case 4://审核失败(不可提现)
            case 10://已处罚
                orderVO.setStatus(OrderStatus.EXPIRED);
                break;
        }
        return orderVO;
    }
}
