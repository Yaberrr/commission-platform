package com.moe.auth.feign.wechat.vo;

import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/4/9
 */
@Data
public class WechatBaseVO {

    //错误码
    private String errcode;

    //错误信息
    private String errmsg;

}
