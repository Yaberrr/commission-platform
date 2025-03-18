package com.moe.auth.feign.aurora.vo;

/**
 * @author tangyabo
 * @date 2025/3/18
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExID() {
        return exID;
    }

    public void setExID(String exID) {
        this.exID = exID;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
