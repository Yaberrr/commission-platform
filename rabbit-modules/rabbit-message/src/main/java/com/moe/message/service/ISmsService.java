package com.moe.message.service;

import com.moe.common.core.domain.R;
import com.moe.message.dto.SmsDTO;

/**
 * @author tangyabo
 * @date 2025/3/11
 */
public interface ISmsService {

    /**
     * 发送单条短信
     */
    R<?> sendOne(SmsDTO body);

}
