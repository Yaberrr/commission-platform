package com.moe.auth.feign.aurora.vo;

import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/3/18
 */
@Data
public class AuroraLoginVo {

    // 流水号，请求出错时可能为空
    private Long id;

    // 开发者自定义的 id，若请求时为空返回为空
    private String exID;

    // 返回码
    private Integer code;

    // 返回码说明
    private String content;

    // 加密后的手机号码，需用配置在极光的公钥对应的私钥解密
    private String phone;

}
