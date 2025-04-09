package com.moe.auth.feign.aurora.body;

import lombok.Data;

/**
 * @author tangyabo
 * @date 2025/3/18
 */
@Data
public class AuroraLoginBody {

    //极光token
    private String loginToken;

    // 开发者自定义的 id
    private String exID;

}
