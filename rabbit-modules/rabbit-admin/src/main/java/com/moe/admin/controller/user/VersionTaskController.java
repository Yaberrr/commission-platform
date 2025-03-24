package com.moe.admin.controller.user;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.VersionTaskDTO;
import com.moe.admin.domain.vo.user.VersionTaskVO;
import com.moe.admin.service.VersionTaskService;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/versionTask")
public class VersionTaskController extends BaseController {

    @Autowired
    private VersionTaskService versionTaskService;

    @RequiresPermissions("admin:versionTask:list")
    @GetMapping("/list")
    public TableDataInfo list()
    {
        Page<VersionTaskVO> list = versionTaskService.selectAllVersionTask(TableSupport.buildPageRequest().buildPage());
        return getDataTable(list);
    }

    @RequiresPermissions("admin:versionTask:remove")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        return AjaxResult.success(versionTaskService.deleteVersionTask(id));
    }

    @RequiresPermissions("admin:versionTask:add")
    @PostMapping
    public AjaxResult add(@RequestBody VersionTaskDTO versionTaskDTO)
    {
        return AjaxResult.success(versionTaskService.addVersionTask(versionTaskDTO));
    }

    @RequiresPermissions("admin:versionTask:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody VersionTaskDTO versionTaskDTO)
    {
        return AjaxResult.success(versionTaskService.editVersionTask(versionTaskDTO));
    }
}
