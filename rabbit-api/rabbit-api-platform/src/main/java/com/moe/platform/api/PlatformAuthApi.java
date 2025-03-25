package com.moe.platform.api;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.platform.vo.AuthUrlVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tangyabo
 * @date 2025/3/25
 */
@FeignResponseCheck(serviceName = "平台授权")
@FeignClient(path = "/platformAuth", contextId = "platformAuthApi", value = ServiceNameConstants.PLATFORM_SERVICE)
public interface PlatformAuthApi {

    /**
     * 生成授权信息
     * @param platformType 平台类型
     * @return
     */
    @PostMapping("/createAuth")
    R<PlatformAuth> createAuth(@RequestParam("platformType") PlatformType platformType,
                               @RequestParam("userId") Long userId);

    /**
     * 检查授权状态
     * @param auth 授权信息
     * @return
     */
    @PostMapping("/checkAuth")
    R<Boolean> checkAuth(@RequestBody PlatformAuth auth);

    /**
     * 生成授权链接
     * @param auth 授权信息
     * @return
     */
    @PostMapping("/generateAuthUrl")
    R<AuthUrlVO> generateAuthUrl(@RequestBody PlatformAuth auth);

}
