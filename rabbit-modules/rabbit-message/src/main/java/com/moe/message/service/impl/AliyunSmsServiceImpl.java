package com.moe.message.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moe.common.core.domain.R;
import com.moe.common.core.exception.ServiceException;
import com.moe.message.body.SmsBody;
import com.moe.message.service.ISmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(AliyunSmsServiceImpl.class);
    @Autowired
    private Client smsClient;
    @Value("${aliyun.sms.signName}")
    private String signName;

    @Override
    public R<?> sendOne(SmsBody body) {
        try {
            SendSmsRequest request = new SendSmsRequest()
                    .setPhoneNumbers(body.getPhoneNumber())
                    .setSignName(signName)
                    .setTemplateCode(body.getTemplate().getCode())
                    .setTemplateParam(new ObjectMapper().writeValueAsString(body.getParam()));
            SendSmsResponse response = smsClient.sendSms(request);
            if("OK".equals(response.getBody().getCode())){
                return R.ok(null,response.getBody().getMessage());
            }else{
                return R.fail(null,response.getBody().getMessage());
            }
        } catch (Exception e){
            log.error("短信服务异常",e);
            throw new ServiceException("短信服务异常");
        }
    }
}
