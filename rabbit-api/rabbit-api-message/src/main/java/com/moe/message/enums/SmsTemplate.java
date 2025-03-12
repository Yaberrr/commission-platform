package com.moe.message.enums;

import cn.hutool.core.map.MapUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * @author tangyabo
 * @date 2025/3/11
 */
@Getter
@AllArgsConstructor
public enum SmsTemplate {

    VERIFY_CODE("SMS_314845112","验证码");

    private final String code;

    private final String desc;

}
