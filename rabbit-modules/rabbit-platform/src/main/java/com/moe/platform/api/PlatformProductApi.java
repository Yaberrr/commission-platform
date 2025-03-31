package com.moe.platform.api;

import cn.hutool.core.collection.CollUtil;
import com.moe.common.core.domain.R;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.platform.dto.product.*;
import com.moe.platform.service.IPlatformProductService;
import com.moe.platform.service.PlatformServiceFactory;
import com.moe.platform.vo.PlatformUrlVO;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台商品api
 * @author tangyabo
 * @date 2025/3/19
 */
@RestController
@RequestMapping("/platformProduct")
public class PlatformProductApi implements IPlatformProductApi {

    @Autowired
    private PlatformServiceFactory platformProductServiceFactory;

    @InnerAuth
    @Override
    public R<TableDataInfo<ProductVO>> list(PlatformProductDTO dto) {
        if(CollUtil.isEmpty(dto.getParamList())){
            throw new ServiceException("未传递参数");
        }
        //todo: 先只处理一个平台
        PlatformParam param = dto.getParamList().get(0);
        IPlatformProductService productService = platformProductServiceFactory.getProductService(param.getPlatformType());
        return R.ok(productService.productList(dto, param));
    }

    @InnerAuth
    @Override
    public R<TableDataInfo<ProductVO>> search(ProductSearchDTO dto) {
        IPlatformProductService productService = platformProductServiceFactory.getProductService(dto.getPlatformType());
        return R.ok(productService.productSearch(dto));
    }

    @InnerAuth
    @Override
    public R<ProductDetailVO> detail(ProductDetailDTO dto) {
        IPlatformProductService productService = platformProductServiceFactory.getProductService(dto.getPlatformType());
        return R.ok(productService.productDetail(dto));
    }

    @InnerAuth
    @Override
    public R<TableDataInfo<ProductVO>> recommend(ProductRecommendDto dto) {
        IPlatformProductService productService = platformProductServiceFactory.getProductService(dto.getPlatformType());
        return R.ok(productService.productRecommend(dto));
    }

    @InnerAuth
    @Override
    public R<PlatformUrlVO> productUrl(ProductDetailDTO dto) {
        IPlatformProductService productService = platformProductServiceFactory.getProductService(dto.getPlatformType());
        return R.ok(productService.productUrl(dto));
    }


}
