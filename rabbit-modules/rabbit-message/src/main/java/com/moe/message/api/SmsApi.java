package com.moe.message.api;

import com.moe.common.core.domain.R;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.message.dto.SmsDTO;
import com.moe.message.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangyabo
 * @date 2025/3/11
 */
@RestController
@RequestMapping("/sms")
public class SmsApi implements ISmsApi {

    @Autowired
    private ISmsService smsService;

    @InnerAuth
    @Override
    public R<?> sendOne(@RequestBody SmsDTO dto) {
        return smsService.sendOne(dto);
    }

}
