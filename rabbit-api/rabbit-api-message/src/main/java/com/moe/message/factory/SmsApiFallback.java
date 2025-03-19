package com.moe.message.factory;

import com.moe.common.core.domain.R;
import com.moe.message.api.SmsApi;
import com.moe.message.body.SmsBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
/**
 * @author tangyabo
 * @date 2025/3/12
 */
@Slf4j
public class SmsApiFallback implements FallbackFactory<SmsApi> {
    @Override
    public SmsApi create(Throwable cause) {
        log.error("短信服务触发降级",cause);

        return new SmsApi() {
            @Override
            public R<?> sendOne(SmsBody dto) {
                return R.fail("发送单条短信服务异常");
            }
        };
    }
}
