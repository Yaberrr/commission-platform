package com.moe.platform.utils;

import com.moe.common.core.exception.ServiceException;
import com.pdd.pop.sdk.http.PopBaseHttpResponse;

/**
 * 拼多多工具类
 */
public class PddUtils {

    /**
     * 生成自定义参数
     * @return
     */
    public static String getCustomParameter(Long userId){
        return "{\"uid\":\""+userId+"\"}";
    }

    /**
     * 检查响应
     * @param response
     */
    public static void checkResponse(PopBaseHttpResponse response){
        if(response.getErrorResponse() != null){
            throw new ServiceException(response.getErrorResponse().getErrorMsg() + "-" + response.getErrorResponse().getSubMsg());
        }
    }
}
