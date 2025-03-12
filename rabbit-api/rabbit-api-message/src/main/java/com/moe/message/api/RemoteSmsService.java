package com.moe.message.api;

import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.message.body.SmsBody;
import com.moe.message.factory.RemoteSmsFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 短信服务
 * @author tangyabo
 * @date 2025/3/11
 */
@FeignClient(value = ServiceNameConstants.MESSAGE_SERVICE, fallbackFactory = RemoteSmsFallback.class)
public interface RemoteSmsService {

    /**
     * 发送单条短信
     */
    @PostMapping("/sms/sendOne")
    R<?> sendOne(@RequestBody SmsBody dto);

}
