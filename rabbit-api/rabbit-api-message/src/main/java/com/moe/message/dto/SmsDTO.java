package com.moe.message.dto;

import com.moe.message.enums.SmsTemplate;
import lombok.Data;

import java.util.Map;

/**
 * @author tangyabo
 * @date 2025/3/12
 */
@Data
public class SmsDTO {

    //手机号
    private String phoneNumber;

    //短信模板
    private SmsTemplate template;

    //模板参数
    private Map<String,String> param;
}
