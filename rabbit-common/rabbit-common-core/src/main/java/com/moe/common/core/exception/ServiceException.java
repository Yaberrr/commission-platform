package com.moe.common.core.exception;

import com.moe.common.core.utils.StringUtils;

/**
 * 业务异常
 *
 * @author ruoyi
 */
public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误信息
     */
    private Object errorData;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException()
    {
    }

    public ServiceException(String message)
    {
        this.message = message;
    }

    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public ServiceException(String message, Integer code, Object errorData){
        this.message = message;
        this.code = code;
        this.errorData = errorData;
    }

    public ServiceException(String messageTemplate, Object... params) {
        this.message = StringUtils.format(messageTemplate, params);
    }

    //message包含具体异常信息
    public ServiceException(Throwable cause, String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params), cause);
        this.message = super.getMessage();
        //保留状态码及错误数据
        if(cause instanceof ServiceException){
            this.code = ((ServiceException)cause).code;
            this.errorData = ((ServiceException)cause).errorData;
        }
    }

    public ServiceException(Integer code, Object errorData, String messageTemplate, Object... params){
        this.message = StringUtils.format(messageTemplate, params);
        this.code = code;
        this.errorData = errorData;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }

    public Object getErrorData() {
        return errorData;
    }

}
