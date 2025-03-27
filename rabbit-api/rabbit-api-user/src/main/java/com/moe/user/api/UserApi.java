package com.moe.user.api;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.OnlyList;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.platform.PlatformType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户服务
 * @author tangyabo
 * @date 2025/3/12
 */
@FeignResponseCheck(serviceName = "用户")
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
