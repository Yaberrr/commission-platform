package com.moe.admin.controller.rule;

import com.moe.admin.service.PolicyConfigService;
import com.moe.admin.service.RuleConfigService;
import com.moe.admin.service.impl.PolicyConfigServiceImpl;
import com.moe.common.core.domain.dto.rule.PolicyConfigDTO;
import com.moe.common.core.domain.dto.rule.RuleConfigDTO;
import com.moe.common.core.domain.vo.rule.PolicyConfigVO;
import com.moe.common.core.domain.vo.rule.RuleConfigVO;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/policyConfig")
public class PolicyConfigController extends BaseController {

    @Autowired
    private PolicyConfigService policyConfigService;

    @RequiresPermissions("admin:policyConfig:list")
    @GetMapping("/list")
    public TableDataInfo list(PolicyConfigDTO policyConfigDTO)
    {
        startPage();
        List<PolicyConfigVO> list = policyConfigService.selectPolicyConfigByDTO(policyConfigDTO);
        return getDataTable(list);
    }

    @RequiresPermissions("admin:policyConfig:query")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(policyConfigService.selectPolicyConfigById(id));
    }

    @RequiresPermissions("admin:policyConfig:add")
    @PostMapping
    public AjaxResult add(@RequestBody PolicyConfigDTO policyConfigDTO)
    {
        return AjaxResult.success(policyConfigService.insertPolicyConfig(policyConfigDTO));
    }

    @RequiresPermissions("admin:policyConfig:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody PolicyConfigDTO policyConfigDTO)
    {
        return AjaxResult.success(policyConfigService.updatePolicyConfig(policyConfigDTO));
    }

    @RequiresPermissions("admin:policyConfig:edit")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        return AjaxResult.success(policyConfigService.deletePolicyConfig(id));
    }
}
