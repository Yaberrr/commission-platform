package com.moe.platform.api;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.platform.dto.order.PlatformOrderDTO;
import com.moe.platform.vo.PlatformOrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/3/31
 */
@FeignResponseCheck(serviceName = "平台订单")
@FeignClient(path = "/platformOrder", contextId = "platformOrderApi", value = ServiceNameConstants.PLATFORM_SERVICE)
public interface IPlatformOrderApi {

    /**
     * 查询订单总数
     * @param dto 查询条件
     * @return
     */
    @PostMapping("/totalCount")
    R<Integer> totalCount(@RequestBody PlatformOrderDTO dto);

    /**
     * 分页查询订单
     * @param dto 查询条件
     * @return
     */
    @PostMapping("/orderList")
    R<List<PlatformOrderVO>> orderList(@RequestBody PlatformOrderDTO dto);
}
