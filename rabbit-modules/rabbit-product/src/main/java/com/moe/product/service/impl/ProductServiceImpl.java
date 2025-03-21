package com.moe.product.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.api.PlatformProductApi;
import com.moe.platform.dto.PlatformProductDTO;
import com.moe.platform.vo.ProductVO;
import com.moe.product.domain.dto.ProductListDTO;
import com.moe.product.service.ProductGroupService;
import com.moe.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tangyabo
 * @date 2025/3/21
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private PlatformProductApi platformProductApi;
    @Autowired
    private ProductGroupService productGroupService;



    @Override
    public TableDataInfo<ProductVO> productList(IPage page, ProductListDTO dto) {
        PlatformProductDTO platformDTO = new PlatformProductDTO();
        platformDTO.setPageNum((int) page.getCurrent());
        platformDTO.setPageSize((int) page.getSize());
        platformDTO.setParamList(productGroupService.platformParamList(dto.getGroupId()));
        return platformProductApi.list(platformDTO);
    }
}
