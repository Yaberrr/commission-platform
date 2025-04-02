package com.moe.platform.api;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.platform.vo.PlatformUrlVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author tangyabo
 * @date 2025/3/25
 */
@FeignResponseCheck(serviceName = "平台授权")
@FeignClient(path = "/platformAuth", contextId = "platformAuthApi", value = ServiceNameConstants.PLATFORM_SERVICE)
public interface IPlatformAuthApi {

    /**
     * 生成授权链接
     * @param platformType
     * @return
     */
    @PostMapping("/generateAuthUrl")
    R<PlatformUrlVO> generateAuthUrl(@RequestParam("platformType") PlatformType platformType);

    /**
     * 查询授权信息
     * @return
     */
    @PostMapping("/authList")
    R<List<PlatformAuth>> authList(@RequestParam("userId") Long userId);

    /**
     * 查询平台的所有授权用户map
     * @return
     */
    @PostMapping("/authMap")
    R<Map<String,Long>> authUserMap(@RequestParam("platformType") PlatformType platformType);


}
