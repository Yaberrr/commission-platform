package com.moe.platform.api;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.dto.product.PlatformProductDTO;
import com.moe.platform.dto.product.ProductDetailDTO;
import com.moe.platform.dto.product.ProductRecommendDto;
import com.moe.platform.dto.product.ProductSearchDTO;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 平台商品服务
 * @author tangyabo
 * @date 2025/3/19
 */
@FeignResponseCheck(serviceName = "平台商品")
@FeignClient(path = "/platformProduct", contextId = "platformProductApi", value = ServiceNameConstants.PLATFORM_SERVICE)
public interface PlatformProductApi {

    /**
     * 平台商品查询
     * @param dto
     * @return
     */
    @PostMapping("/list")
    TableDataInfo<ProductVO> list(@RequestBody PlatformProductDTO dto);

    /**
     * 平台商品搜索
     * @param dto
     * @return
     */
    @PostMapping("/search")
    TableDataInfo<ProductVO> search(@RequestBody ProductSearchDTO dto);

    /**
     * 平台商品详情
     * @return
     */
    @PostMapping("/detail")
    R<ProductDetailVO> detail(@RequestBody ProductDetailDTO dto);

    /**
     * 平台商品推荐
     * @param dto
     * @return
     */
    @PostMapping("/recommend")
    TableDataInfo<ProductVO> recommend(@RequestBody ProductRecommendDto dto);

}
