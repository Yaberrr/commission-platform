package com.moe.user.api;

import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 用户服务
 * @author tangyabo
 * @date 2025/3/12
 */
@FeignClient(value = ServiceNameConstants.USER_SERVICE)
public interface RemoteUserService {

    /**
     * 保存用户信息
     * @param
     * @return
     */
    @PostMapping("/saveUser")
    R<User> saveUser(User user);

}
