package com.moe.admin.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.tutorial.TutorialAddDTO;
import com.moe.admin.domain.dto.tutorial.TutorialSortDTO;
import com.moe.admin.domain.vo.user.TutorialVO;
import com.moe.admin.service.ITutorialService;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutorial")
public class TutorialController extends BaseController {

    @Autowired
    public ITutorialService tutorialService;

    @RequiresPermissions("admin:tutorial:list")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<TutorialVO> list = tutorialService.getTutorialList();
        return getDataTable(list);
    }

    @RequiresPermissions("admin:tutorial:query")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable("id") Long id) {
        return AjaxResult.success(tutorialService.selectByTutorialId(id));
    }

    @RequiresPermissions("admin:tutorial:add")
    @PostMapping
    public AjaxResult add(@RequestBody TutorialAddDTO tutorialAddDTO) {
        return AjaxResult.success(tutorialService.addTutorial(tutorialAddDTO));
    }

    @RequiresPermissions("admin:tutorial:update")
    @PutMapping
    public AjaxResult update(@RequestBody TutorialAddDTO tutorialAddDTO) {
        return AjaxResult.success(tutorialService.updateTutorial(tutorialAddDTO));
    }

    @RequiresPermissions("admin:tutorial:add")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id) {
        return AjaxResult.success(tutorialService.deleteTutorial(id));
    }

    @RequiresPermissions("admin:tutorial:sort")
    @PostMapping("/sort")
    public AjaxResult sort(@RequestBody TutorialSortDTO tutorialSortDTO) {
        return AjaxResult.success(tutorialService.sortTutorial(tutorialSortDTO));
    }
}
