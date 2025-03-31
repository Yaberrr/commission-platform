package com.moe.product.controller;

import com.moe.common.core.domain.R;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.platform.dto.product.ProductDetailDTO;
import com.moe.platform.dto.product.ProductRecommendDto;
import com.moe.platform.dto.product.ProductSearchDTO;
import com.moe.platform.vo.PlatformUrlVO;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import com.moe.product.domain.dto.ProductListDTO;
import com.moe.product.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Tag(name = "商品")
@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @Operation(description = "商品列表")
    @PostMapping("/list")
    public R<TableDataInfo<ProductVO>> list(@Valid ProductListDTO dto){
        return productService.productList(TableSupport.buildPageRequest().buildPage(),dto);
    }

    @Operation(description = "商品搜索")
    @PostMapping("/search")
    public R<TableDataInfo<ProductVO>> search(@Valid ProductSearchDTO dto){
        return productService.productSearch(dto);
    }

    @Operation(description = "商品详情")
    @PostMapping("/detail")
    public R<ProductDetailVO> detail(@Valid ProductDetailDTO dto){
        return productService.productDetail(dto);
    }

    @Operation(description = "商品推荐")
    @PostMapping("/recommend")
    public R<TableDataInfo<ProductVO>> recommend(@Valid ProductRecommendDto dto){
        return productService.productRecommend(dto);
    }

    @Operation(description = "生成商品链接")
    @PostMapping("/productUrl")
    public R<PlatformUrlVO> productUrl(@Valid ProductDetailDTO dto){
        return productService.productUrl(dto);
    }

}
