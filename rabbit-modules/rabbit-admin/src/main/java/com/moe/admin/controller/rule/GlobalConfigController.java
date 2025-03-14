package com.moe.admin.controller.rule;

import com.moe.admin.service.GlobalConfigService;
import com.moe.common.core.domain.dto.rule.GlobalConfigDTO;
import com.moe.common.core.domain.dto.user.UserFeedBackDTO;
import com.moe.common.core.domain.vo.user.UserFeedbackVO;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/globalConfig")
public class GlobalConfigController {

    @Autowired
    private GlobalConfigService globalConfigService;

    @RequiresPermissions("admin:globalConfig:query")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable("id") Integer configType)
    {
        return AjaxResult.success(globalConfigService.selectGlobalConfigByConfigType(configType));
    }

    @RequiresPermissions("admin:globalConfig:edit")
    @PostMapping
    public AjaxResult edit(@RequestBody GlobalConfigDTO globalConfigDTO)
    {
        return AjaxResult.success(globalConfigService.updateGlobalConfigByConfigType(globalConfigDTO));
    }

}
