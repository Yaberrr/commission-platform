package com.moe.platform.controller;

import cn.hutool.core.collection.CollUtil;
import com.moe.common.core.domain.R;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.platform.api.PlatformProductApi;
import com.moe.platform.dto.product.*;
import com.moe.platform.service.PlatformProductService;
import com.moe.platform.service.PlatformServiceFactory;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台商品api
 * @author tangyabo
 * @date 2025/3/19
 */
@RestController
@RequestMapping("/platformProduct")
public class PlatformProductController implements PlatformProductApi {

    @Autowired
    private PlatformServiceFactory platformProductServiceFactory;

    @InnerAuth
    @Override
    @PostMapping("/list")
    public TableDataInfo<ProductVO> list(PlatformProductDTO dto) {
        if(CollUtil.isEmpty(dto.getParamList())){
            throw new ServiceException("未传递参数");
        }
        //todo: 先只处理一个平台
        PlatformParam param = dto.getParamList().get(0);
        PlatformProductService productService = platformProductServiceFactory.getProductService(param.getPlatformType());
        return productService.productList(dto, param);
    }

    @InnerAuth
    @Override
    @PostMapping("/search")
    public TableDataInfo<ProductVO> search(ProductSearchDTO dto) {
        PlatformProductService productService = platformProductServiceFactory.getProductService(dto.getPlatformType());
        return productService.productSearch(dto);
    }

    @InnerAuth
    @Override
    @PostMapping("/detail")
    public R<ProductDetailVO> detail(ProductDetailDTO dto) {
        PlatformProductService productService = platformProductServiceFactory.getProductService(dto.getPlatformType());
        return R.ok(productService.productDetail(dto));
    }

    @Override
    public TableDataInfo<ProductVO> recommend(ProductRecommendDto dto) {
        PlatformProductService productService = platformProductServiceFactory.getProductService(dto.getPlatformType());
        return productService.productRecommend(dto);
    }


}
