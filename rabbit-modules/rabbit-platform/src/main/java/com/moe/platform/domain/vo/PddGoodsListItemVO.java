package com.moe.platform.domain.vo;

import com.pdd.pop.ext.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * pdd包装类  补全set方法用于对象拷贝
 * @author tangyabo
 * @date 2025/3/27
 */
@Data
public class PddGoodsListItemVO{
    @JsonProperty("activity_promotion_rate")
    private Long activityPromotionRate;
    @JsonProperty("activity_tags")
    private List<Integer> activityTags;
    @JsonProperty("activity_type")
    private Integer activityType;
    @JsonProperty("brand_name")
    private String brandName;
    @JsonProperty("cash_gift_amount")
    private Long cashGiftAmount;
    @JsonProperty("cat_ids")
    private List<Long> catIds;
    @JsonProperty("clt_cpn_batch_sn")
    private String cltCpnBatchSn;
    @JsonProperty("clt_cpn_discount")
    private Long cltCpnDiscount;
    @JsonProperty("clt_cpn_end_time")
    private Long cltCpnEndTime;
    @JsonProperty("clt_cpn_min_amt")
    private Long cltCpnMinAmt;
    @JsonProperty("clt_cpn_quantity")
    private Long cltCpnQuantity;
    @JsonProperty("clt_cpn_remain_quantity")
    private Long cltCpnRemainQuantity;
    @JsonProperty("clt_cpn_start_time")
    private Long cltCpnStartTime;
    @JsonProperty("coupon_discount")
    private Long couponDiscount;
    @JsonProperty("coupon_end_time")
    private Long couponEndTime;
    @JsonProperty("coupon_min_order_amount")
    private Long couponMinOrderAmount;
    @JsonProperty("coupon_remain_quantity")
    private Long couponRemainQuantity;
    @JsonProperty("coupon_start_time")
    private Long couponStartTime;
    @JsonProperty("coupon_total_quantity")
    private Long couponTotalQuantity;
    @JsonProperty("create_at")
    private Long createAt;
    @JsonProperty("desc_txt")
    private String descTxt;
    @JsonProperty("extra_coupon_amount")
    private Long extraCouponAmount;
    @JsonProperty("goods_desc")
    private String goodsDesc;
    @JsonProperty("goods_image_url")
    private String goodsImageUrl;
    @JsonProperty("goods_labels")
    private List<Integer> goodsLabels;
    @JsonProperty("goods_name")
    private String goodsName;
    @JsonProperty("goods_sign")
    private String goodsSign;
    @JsonProperty("goods_thumbnail_url")
    private String goodsThumbnailUrl;
    @JsonProperty("has_coupon")
    private Boolean hasCoupon;
    @JsonProperty("has_mall_coupon")
    private Boolean hasMallCoupon;
    @JsonProperty("has_material")
    private Boolean hasMaterial;
    @JsonProperty("is_multi_group")
    private Boolean isMultiGroup;
    @JsonProperty("lgst_txt")
    private String lgstTxt;
    @JsonProperty("mall_coupon_discount_pct")
    private Integer mallCouponDiscountPct;
    @JsonProperty("mall_coupon_end_time")
    private Long mallCouponEndTime;
    @JsonProperty("mall_coupon_id")
    private Long mallCouponId;
    @JsonProperty("mall_coupon_max_discount_amount")
    private Integer mallCouponMaxDiscountAmount;
    @JsonProperty("mall_coupon_min_order_amount")
    private Integer mallCouponMinOrderAmount;
    @JsonProperty("mall_coupon_remain_quantity")
    private Long mallCouponRemainQuantity;
    @JsonProperty("mall_coupon_start_time")
    private Long mallCouponStartTime;
    @JsonProperty("mall_coupon_total_quantity")
    private Long mallCouponTotalQuantity;
    @JsonProperty("mall_cps")
    private Integer mallCps;
    @JsonProperty("mall_id")
    private Long mallId;
    @JsonProperty("mall_name")
    private String mallName;
    @JsonProperty("merchant_type")
    private Integer merchantType;
    @JsonProperty("min_group_price")
    private Long minGroupPrice;
    @JsonProperty("min_normal_price")
    private Long minNormalPrice;
    @JsonProperty("only_scene_auth")
    private Boolean onlySceneAuth;
    @JsonProperty("opt_id")
    private Long optId;
    @JsonProperty("opt_ids")
    private List<Long> optIds;
    @JsonProperty("opt_name")
    private String optName;
    @JsonProperty("plan_type")
    private Integer planType;
    @JsonProperty("predict_promotion_rate")
    private Long predictPromotionRate;
    @JsonProperty("promotion_rate")
    private Long promotionRate;
    @JsonProperty("sales_tip")
    private String salesTip;
    @JsonProperty("search_id")
    private String searchId;
    @JsonProperty("serv_txt")
    private String servTxt;
    @JsonProperty("service_tags")
    private List<Long> serviceTags;
    @JsonProperty("share_rate")
    private Integer shareRate;
    @JsonProperty("subsidy_amount")
    private Integer subsidyAmount;
    @JsonProperty("subsidy_duo_amount_ten_million")
    private Integer subsidyDuoAmountTenMillion;
    @JsonProperty("subsidy_goods_type")
    private Integer subsidyGoodsType;
    @JsonProperty("unified_tags")
    private List<String> unifiedTags;
    @JsonProperty("zs_duo_id")
    private Long zsDuoId;
    @JsonProperty("mall_sn")
    private String mallSn;
}
