package com.moe.product.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moe.common.core.domain.R;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.api.PlatformProductApi;
import com.moe.platform.dto.product.PlatformProductDTO;
import com.moe.platform.dto.product.ProductDetailDTO;
import com.moe.platform.dto.product.ProductRecommendDto;
import com.moe.platform.dto.product.ProductSearchDTO;
import com.moe.platform.vo.PlatformUrlVO;
import com.moe.platform.vo.ProductDetailVO;
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
        //查询商品组包含的参数
        platformDTO.setParamList(productGroupService.platformParamList(dto.getGroupId()));
        return platformProductApi.list(platformDTO);
    }

    @Override
    public TableDataInfo<ProductVO> productSearch(ProductSearchDTO dto) {
        return platformProductApi.search(dto);
    }

    @Override
    public R<ProductDetailVO> productDetail(ProductDetailDTO dto) {
        return platformProductApi.detail(dto);
    }

    @Override
    public TableDataInfo<ProductVO> productRecommend(ProductRecommendDto dto) {
        return platformProductApi.recommend(dto);
    }

    @Override
    public R<PlatformUrlVO> productUrl(ProductDetailDTO dto) {
        return platformProductApi.productUrl(dto);
    }

}
