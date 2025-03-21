package com.moe.platform.controller;

import cn.hutool.core.collection.CollUtil;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.platform.api.PlatformProductApi;
import com.moe.platform.dto.PlatformProductDTO;
import com.moe.platform.dto.PlatformParam;
import com.moe.platform.service.PlatformProductService;
import com.moe.platform.service.PlatformProductServiceFactory;
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
@RequestMapping("/product")
public class PlatformProductController implements PlatformProductApi {

    @Autowired
    private PlatformProductServiceFactory platformProductServiceFactory;

    /**
     * 商品列表
     * @return
     */
    @InnerAuth
    @Override
    @PostMapping("/list")
    public TableDataInfo<ProductVO> list(PlatformProductDTO dto) {
        if(CollUtil.isEmpty(dto.getParamList())){
            throw new ServiceException("未指定平台");
        }
        //todo: 先只处理一个平台
        PlatformParam param = dto.getParamList().get(0);
        PlatformProductService productService = platformProductServiceFactory.getProductService(param.getPlatformType());
        return productService.productList(dto, param);
    }
}
