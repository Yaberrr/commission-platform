package com.moe.platform.service;

import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.dto.PlatformProductDTO;
import com.moe.platform.dto.PlatformParam;
import com.moe.platform.dto.PlatformSearchDTO;
import com.moe.platform.vo.ProductVO;

/**
 * 商品
 * @author tangyabo
 * @date 2025/3/19
 */
public interface PlatformProductService {

    /**
     * 商品列表
     * @param dto
     * @param param
     * @return
     */
    TableDataInfo<ProductVO> productList(PlatformProductDTO dto, PlatformParam param);

    /**
     * 商品搜索
     * @param dto
     * @return
     */
    TableDataInfo<ProductVO> productSearch(PlatformSearchDTO dto);
}
