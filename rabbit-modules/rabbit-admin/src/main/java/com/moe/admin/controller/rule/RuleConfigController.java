package com.moe.admin.controller.rule;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.service.RuleConfigService;
import com.moe.admin.domain.dto.rule.RuleConfigDTO;
import com.moe.admin.domain.vo.rule.RuleConfigVO;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ruleConfig")
public class RuleConfigController extends BaseController {

    @Autowired
    private RuleConfigService ruleConfigService;

    @RequiresPermissions("admin:ruleConfig:list")
    @GetMapping("/list")
    public TableDataInfo list(RuleConfigDTO ruleConfigDTO)
    {
        Page<RuleConfigVO> list = ruleConfigService.selectRuleConfigVOByUser(TableSupport.buildPageRequest().buildPage(), ruleConfigDTO);
        return getDataTable(list);
    }

    @RequiresPermissions("admin:ruleConfig:query")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ruleConfigService.selectRuleConfigVOById(id));
    }

    @RequiresPermissions("admin:ruleConfig:add")
    @PostMapping
    public AjaxResult add(@RequestBody RuleConfigDTO ruleConfigDTO)
    {
        return AjaxResult.success(ruleConfigService.addRuleConfig(ruleConfigDTO));
    }

    @RequiresPermissions("admin:ruleConfig:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody RuleConfigDTO ruleConfigDTO)
    {
        return AjaxResult.success(ruleConfigService.updateRuleConfig(ruleConfigDTO));
    }

    @RequiresPermissions("admin:ruleConfig:remove")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ruleConfigService.deleteRuleConfigById(id));
    }
}
