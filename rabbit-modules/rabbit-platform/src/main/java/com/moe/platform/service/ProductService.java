package com.moe.platform.service;

import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.body.SearchBody;
import com.moe.platform.body.SearchParam;
import com.moe.platform.vo.ProductVO;

/**
 * 商品
 * @author tangyabo
 * @date 2025/3/19
 */
public interface ProductService {

    TableDataInfo<ProductVO> search(SearchBody body, SearchParam param);

}
