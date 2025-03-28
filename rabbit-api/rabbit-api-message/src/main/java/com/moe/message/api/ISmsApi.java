package com.moe.message.api;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.message.dto.SmsDTO;
import com.moe.message.factory.SmsApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 短信服务
 * @author tangyabo
 * @date 2025/3/11
 */
@FeignResponseCheck(serviceName = "短信")
@FeignClient(path = "/sms", contextId = "smsApi", value = ServiceNameConstants.MESSAGE_SERVICE, fallbackFactory = SmsApiFallback.class)
public interface ISmsApi {

    /**
     * 发送单条短信
     */
    @PostMapping("/sendOne")
    R<?> sendOne(@RequestBody SmsDTO dto);

}
