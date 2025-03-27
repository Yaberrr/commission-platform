package com.moe.message.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moe.common.core.domain.R;
import com.moe.common.core.exception.ServiceException;
import com.moe.message.dto.SmsDTO;
import com.moe.message.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信服务
 * @author tangyabo
 * @date 2025/3/11
 */
@Service
public class AliyunSmsServiceImpl implements ISmsService {

    @Autowired
    private Client smsClient;
    @Value("${aliyun.sms.signName}")
    private String signName;

    @Override
    public R<?> sendOne(SmsDTO body) {
        try {
            SendSmsRequest request = new SendSmsRequest()
                    .setPhoneNumbers(body.getPhoneNumber())
                    .setSignName(signName)
                    .setTemplateCode(body.getTemplate().getCode())
                    .setTemplateParam(new ObjectMapper().writeValueAsString(body.getParam()));
            SendSmsResponse response = smsClient.sendSms(request);
            if(!"OK".equals(response.getBody().getCode())){
                throw new ServiceException(response.getBody().getMessage());
            }
            return R.ok(null,response.getBody().getMessage());
        } catch (Exception e){
            throw new ServiceException(e,"发送单条短信api异常:{}",e.getMessage());
        }
    }
}
