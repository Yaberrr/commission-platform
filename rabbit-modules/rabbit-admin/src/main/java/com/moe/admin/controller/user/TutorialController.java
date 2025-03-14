package com.moe.admin.controller.user;

import com.moe.admin.service.TutorialService;
import com.moe.common.core.domain.dto.tutorial.TutorialAddDTO;
import com.moe.common.core.domain.dto.tutorial.TutorialSortDTO;
import com.moe.common.core.domain.dto.user.UserDTO;
import com.moe.common.core.domain.vo.user.TutorialVO;
import com.moe.common.core.domain.vo.user.UserVO;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tutorial")
public class TutorialController extends BaseController {

    @Autowired
    public TutorialService tutorialService;

    @RequiresPermissions("admin:tutorial:list")
    @GetMapping("/list")
    public TableDataInfo list()
    {
        startPage();
        List<TutorialVO> list = tutorialService.getAllTutorial();
        return getDataTable(list);
    }

    @RequiresPermissions("admin:tutorial:query")
    @GetMapping("/{id}")
    public AjaxResult list(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tutorialService.selectByTutorialId(id));
    }

    @RequiresPermissions("admin:tutorial:add")
    @PostMapping
    public AjaxResult add(@RequestBody TutorialAddDTO tutorialAddDTO)
    {
        return AjaxResult.success(tutorialService.addTutorial(tutorialAddDTO));
    }

    @RequiresPermissions("admin:tutorial:add")
    @PutMapping
    public AjaxResult update(@RequestBody TutorialAddDTO tutorialAddDTO)
    {
        return AjaxResult.success(tutorialService.updateTutorial(tutorialAddDTO));
    }

    @RequiresPermissions("admin:tutorial:add")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tutorialService.deleteTutorial(id));
    }

    @RequiresPermissions("admin:tutorial:edit")
    @PostMapping("/sort")
    public AjaxResult sort(@RequestBody TutorialSortDTO tutorialSortDTO)
    {
        return AjaxResult.success(tutorialService.sortTutorial(tutorialSortDTO));
    }
}
