package com.moe.product.controller;

import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.platform.vo.ProductVO;
import com.moe.product.domain.dto.ProductListDTO;
import com.moe.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 商品
 * @author tangyabo
 * @date 2025/3/20
 */
@RestController
@RequestMapping
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 商品列表
     * @return
     */
    @PostMapping("/list")
    public TableDataInfo<ProductVO> list(@Valid ProductListDTO dto){
        return productService.productList(TableSupport.buildPageRequest().buildPage(),dto);
    }
}
