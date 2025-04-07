package com.moe.platform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 平台url
 * @author tangyabo
 * @date 2025/3/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUrlVO {

    //app链接
    private String mobileUrl;

    //schema链接
    private String schemaUrl;

    //微信链接
    private String weixinUrl;

}
