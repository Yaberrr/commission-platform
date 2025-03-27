package com.moe.product.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moe.common.core.domain.R;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.api.PlatformProductApi;
import com.moe.platform.dto.PlatformProductDTO;
import com.moe.platform.dto.PlatformProductDetailDTO;
import com.moe.platform.dto.PlatformSearchDTO;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import com.moe.product.domain.dto.ProductDetailDTO;
import com.moe.product.domain.dto.ProductListDTO;
import com.moe.product.domain.dto.ProductSearchDTO;
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
        //查询商品组包含的参数
        platformDTO.setParamList(productGroupService.platformParamList(dto.getGroupId()));
        return platformProductApi.list(platformDTO);
    }

    @Override
    public TableDataInfo<ProductVO> productSearch(IPage page, ProductSearchDTO dto) {
        PlatformSearchDTO platformDTO = new PlatformSearchDTO();
        platformDTO.setPageNum((int) page.getCurrent());
        platformDTO.setPageSize((int) page.getSize());
        platformDTO.setKeyword(dto.getKeyword());
        platformDTO.setPlatformType(dto.getPlatformType());
        return platformProductApi.search(platformDTO);
    }

    @Override
    public R<ProductDetailVO> productDetail(ProductDetailDTO dto) {
        PlatformProductDetailDTO platformDTO = new PlatformProductDetailDTO();
        platformDTO.setProductId(dto.getProductId());
        platformDTO.setPlatformType(dto.getPlatformType());
        platformDTO.setSearchParam(dto.getSearchParam());
        return platformProductApi.detail(platformDTO);
    }
}
