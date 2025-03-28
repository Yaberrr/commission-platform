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

/**
 * @author tangyabo
 * @date 2025/3/25
 */
@FeignResponseCheck(serviceName = "平台授权")
@FeignClient(path = "/platformAuth", contextId = "platformAuthApi", value = ServiceNameConstants.PLATFORM_SERVICE)
public interface PlatformAuthApi {

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


}
