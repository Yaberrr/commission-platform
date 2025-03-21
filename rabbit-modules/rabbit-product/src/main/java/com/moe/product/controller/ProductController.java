package com.moe.product.controller;

import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.platform.vo.ProductVO;
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
    public TableDataInfo<ProductVO> list(@Valid ProductListDTO dto){
        return productService.productList(TableSupport.buildPageRequest().buildPage(),dto);
    }


    @Operation(description = "商品搜索")
    @PostMapping("/search")
    public TableDataInfo<ProductVO> search(@Valid ProductSearchDTO dto){
        return productService.productSearch(TableSupport.buildPageRequest().buildPage(),dto);
    }
}
