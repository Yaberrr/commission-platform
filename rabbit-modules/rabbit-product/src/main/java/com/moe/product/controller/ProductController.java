package com.moe.product.controller;

import com.moe.common.core.domain.R;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import com.moe.product.domain.dto.ProductDetailDTO;
import com.moe.product.domain.dto.ProductListDTO;
import com.moe.product.domain.dto.ProductSearchDTO;
import com.moe.product.service.ProductService;
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
    private ProductService productService;

    @Operation(description = "商品列表")
    @PostMapping("/list")
    public R<TableDataInfo<ProductVO>> list(@Valid ProductListDTO dto){
        return R.ok(productService.productList(TableSupport.buildPageRequest().buildPage(),dto));
    }


    @Operation(description = "商品搜索")
    @PostMapping("/search")
    public R<TableDataInfo<ProductVO>> search(@Valid ProductSearchDTO dto){
        return R.ok(productService.productSearch(TableSupport.buildPageRequest().buildPage(),dto));
    }

    @Operation(description = "商品详情")
    @PostMapping("/detail")
    public R<ProductDetailVO> detail(@Valid ProductDetailDTO dto){
        return productService.productDetail(dto);
    }

}
