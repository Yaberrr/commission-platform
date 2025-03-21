package com.moe.auth.feign.aurora;

import com.moe.auth.feign.aurora.body.AuroraLoginBody;
import com.moe.auth.feign.aurora.vo.AuroraLoginVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 极光api
 * @author tangyabo
 * @date 2025/3/18
 */
@FeignClient(name = "auroraApi", contextId = "auroraApi", url = "https://api.verification.jpush.cn", configuration = AuroraFeignConfiguration.class)
public interface AuroraApi {

    /**
     * 一键登录
     * @param body
     * @return
     */
    @PostMapping(value = "/v1/web/loginTokenVerify")
    AuroraLoginVo loginTokenVerify(@RequestBody AuroraLoginBody body);

}
