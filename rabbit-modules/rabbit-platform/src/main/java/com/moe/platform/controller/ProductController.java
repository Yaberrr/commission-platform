package com.moe.platform.controller;

import cn.hutool.core.collection.CollUtil;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.platform.api.PlatformProductApi;
import com.moe.platform.body.SearchBody;
import com.moe.platform.body.SearchParam;
import com.moe.platform.service.ProductService;
import com.moe.platform.service.ProductServiceFactory;
import com.moe.platform.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台商品
 * @author tangyabo
 * @date 2025/3/19
 */
@RestController
@RequestMapping("/product")
public class ProductController implements PlatformProductApi {

    @Autowired
    private ProductServiceFactory productServiceFactory;

    /**
     * 商品搜索
     * @param body
     * @return
     */
    @InnerAuth
    @Override
    @PostMapping("/search")
    public TableDataInfo<ProductVO> search(SearchBody body) {
        if(CollUtil.isEmpty(body.getParamList())){
            throw new ServiceException("未指定平台");
        }
        //todo: 先只处理一个平台
        SearchParam param = body.getParamList().get(0);
        ProductService productService = productServiceFactory.getProductService(param.getPlatformType());
        return productService.search(body, param);
    }
}
