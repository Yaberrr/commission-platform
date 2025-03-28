package com.moe.message.factory;

import com.moe.common.core.domain.R;
import com.moe.message.api.ISmsApi;
import com.moe.message.dto.SmsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
/**
 * @author tangyabo
 * @date 2025/3/12
 */
@Slf4j
public class SmsApiFallback implements FallbackFactory<ISmsApi> {
    @Override
    public ISmsApi create(Throwable cause) {
        log.error("短信服务触发降级");
        return new ISmsApi() {
            @Override
            public R<?> sendOne(SmsDTO dto) {
                return R.fail(cause.getMessage());
            }
        };
    }
}
