package com.moe.auth.feign.aurora.body;

/**
 * @author tangyabo
 * @date 2025/3/18
 */
public class AuroraLoginBody {

    //极光token
    private String loginToken;

    // 开发者自定义的 id
    private String exID;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getExID() {
        return exID;
    }

    public void setExID(String exID) {
        this.exID = exID;
    }
}
