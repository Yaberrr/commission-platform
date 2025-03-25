package com.moe.platform.vo;

import lombok.Data;

/**
 * 授权相关信息
 * @author tangyabo
 * @date 2025/3/25
 */
@Data
public class AuthUrlVO {

    //h5链接
    private String h5Url;

    //app链接
    private String mobileUrl;

}
