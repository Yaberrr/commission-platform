package com.moe.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.config.PlatformConfig;
import com.moe.common.core.domain.product.ProductFavorite;
import com.moe.common.core.enums.config.PlatformConfigType;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.module.service.PlatformConfigService;
import com.moe.common.module.utils.CommissionUtils;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.platform.api.IPlatformProductApi;
import com.moe.platform.dto.product.PlatformProductDTO;
import com.moe.platform.dto.product.ProductDetailDTO;
import com.moe.platform.dto.product.ProductRecommendDto;
import com.moe.platform.dto.product.ProductSearchDTO;
import com.moe.platform.vo.PlatformUrlVO;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import com.moe.product.domain.dto.CancelFavoriteDTO;
import com.moe.product.domain.dto.ProductFavoriteDTO;
import com.moe.product.domain.dto.ProductListDTO;
import com.moe.product.mapper.ProductFavoriteMapper;
import com.moe.product.service.IProductGroupService;
import com.moe.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * @author tangyabo
 * @date 2025/3/21
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IPlatformProductApi platformProductApi;
    @Autowired
    private IProductGroupService productGroupService;
    @Autowired
    private PlatformConfigService platformConfigService;

    @Autowired
    private ProductFavoriteMapper productFavoriteMapper;

    @Override
    public R<TableDataInfo<ProductVO>> productList(IPage page, ProductListDTO dto) {
        PlatformProductDTO platformDTO = new PlatformProductDTO();
        platformDTO.setPageNum((int) page.getCurrent());
        platformDTO.setPageSize((int) page.getSize());
        //查询商品组包含的参数
        platformDTO.setParamList(productGroupService.platformParamList(dto.getGroupId()));

        R<TableDataInfo<ProductVO>> r = platformProductApi.list(platformDTO);
        this.resetCommission(r.getData());
        return r;
    }

    @Override
    public R<TableDataInfo<ProductVO>> productSearch(ProductSearchDTO dto) {
        R<TableDataInfo<ProductVO>> r = platformProductApi.search(dto);
        this.resetCommission(r.getData());
        return r;
    }

    @Override
    public R<ProductDetailVO> productDetail(ProductDetailDTO dto) {
        R<ProductDetailVO> r = platformProductApi.detail(dto);
        ProductDetailVO detail = r.getData();
        //获取佣金配置
        MemberLevel level = SecurityUtils.getMemberLevel();
        PlatformConfig.CommissionRatio config = platformConfigService.getConfig(detail.getPlatformType(), PlatformConfigType.COMMISSION_RATIO);
        BigDecimal commission = detail.getCommission();
        detail.setCommission(CommissionUtils.calculate(config, commission, level).getCommission());
        //计算下一级佣金
        if (level.nextLevel() != null) {
            detail.setNextCommission(CommissionUtils.calculate(config, commission, level.nextLevel()).getCommission());
        }
        //todo: 分享佣金暂时为0
        detail.setShareCommission(BigDecimal.ZERO);
        return r;
    }

    @Override
    public R<TableDataInfo<ProductVO>> productRecommend(ProductRecommendDto dto) {
        R<TableDataInfo<ProductVO>> result = platformProductApi.recommend(dto);
        this.resetCommission(result.getData());
        return result;
    }

    @Override
    public R<PlatformUrlVO> productUrl(ProductDetailDTO dto) {
        return platformProductApi.productUrl(dto);
    }

    @Override
    public R<ProductVO> addFavorite(ProductFavoriteDTO dto) {
        ProductFavorite productFavorite = new ProductFavorite();
        if (SecurityUtils.getAppUser() != null) {
            productFavorite.setUserId(SecurityUtils.getAppUser().getId());
        } else {
            throw new RuntimeException("未登录");
        }
        QueryWrapper<ProductFavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", dto.getProductId());
        queryWrapper.eq("user_id", productFavorite.getUserId());
        if (productFavoriteMapper.selectCount(queryWrapper) > 0) {
            return R.fail("已收藏");
        }

        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        productDetailDTO.setProductId(dto.getProductId());
        productDetailDTO.setPlatformType(dto.getPlatformType().getCode());
        R<ProductDetailVO> r = platformProductApi.detail(productDetailDTO);
        ProductDetailVO detailVO = r.getData();

        productFavorite.setProductId(detailVO.getProductId());
        productFavorite.setProductName(detailVO.getProductName());
        productFavorite.setPrice(detailVO.getPrice());
        productFavorite.setLowestPrice(detailVO.getLowestPrice());
        if (detailVO.getBestCoupon() != null) {
            productFavorite.setCouponDiscount(detailVO.getBestCoupon().getDiscount());
        }
        productFavorite.setCommission(detailVO.getCommission());
        productFavorite.setPlatformType(detailVO.getPlatformType());
        productFavoriteMapper.insert(productFavorite);
        return R.ok(detailVO);
    }

    @Override
    public R<Boolean> cancelFavorite(CancelFavoriteDTO dto) {
        QueryWrapper<ProductFavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", dto.getProductIds());
        if (SecurityUtils.getAppUser()!= null) {
            queryWrapper.eq("user_id", SecurityUtils.getAppUser().getId());
        }
        productFavoriteMapper.delete(queryWrapper);
        return R.ok(true);
    }

    /**
     * 重新设置佣金
     */
    private void resetCommission(TableDataInfo<ProductVO> dataInfo) {
        dataInfo.getRows().stream().collect(Collectors.groupingBy(ProductVO::getPlatformType))
                .forEach((type, list) -> {
                    //获取佣金配置
                    PlatformConfig.CommissionRatio config = platformConfigService.getConfig(type, PlatformConfigType.COMMISSION_RATIO);
                    for (ProductVO vo : list) {
                        MemberLevel level = SecurityUtils.getMemberLevel();
                        vo.setCommission(CommissionUtils.calculate(config, vo.getCommission(), level).getCommission());
                    }
                });
    }

}
