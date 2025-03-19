package com.moe.user.api;

import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务
 * @author tangyabo
 * @date 2025/3/12
 */
@FeignClient(contextId = "userApi", value = ServiceNameConstants.USER_SERVICE)
public interface UserApi {

    /**
     * 保存用户信息
     * @param
     * @return
     */
    @PostMapping("/saveUser")
    R<User> saveUser(@RequestBody User user);

}
