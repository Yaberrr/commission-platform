package com.moe.platform.api;

import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.body.SearchBody;
import com.moe.platform.vo.ProductVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 平台商品服务
 * @author tangyabo
 * @date 2025/3/19
 */
@FeignClient(contextId = "platformProductApi", value = ServiceNameConstants.PLATFORM_SERVICE)
public interface PlatformProductApi {

    /**
     * 商品查询
     * @param body
     * @return
     */
    @PostMapping("/product/search")
    TableDataInfo<ProductVO> search(@RequestBody SearchBody body);


}
