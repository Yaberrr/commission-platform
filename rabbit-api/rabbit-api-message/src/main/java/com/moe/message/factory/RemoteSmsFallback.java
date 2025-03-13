package com.moe.message.factory;

import com.moe.common.core.domain.R;
import com.moe.message.api.RemoteSmsService;
import com.moe.message.body.SmsBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
/**
 * @author tangyabo
 * @date 2025/3/12
 */
@Slf4j
public class RemoteSmsFallback implements FallbackFactory<RemoteSmsService> {
    @Override
    public RemoteSmsService create(Throwable cause) {
        log.error("短信服务触发降级",cause);

        return new RemoteSmsService() {
            @Override
            public R<?> sendOne(SmsBody dto) {
                return R.fail("发送单条短信接口异常");
            }
        };
    }
}
