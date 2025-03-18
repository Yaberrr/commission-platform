package com.moe.admin.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.service.UserFeedbackService;
import com.moe.admin.domain.dto.user.UserFeedBackDTO;
import com.moe.admin.domain.dto.user.UserFeedbackUpdateDTO;
import com.moe.admin.domain.vo.user.UserFeedbackVO;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userFeedback")
public class UserFeedbackController extends BaseController {

    @Autowired
    private UserFeedbackService userFeedbackService;

    @RequiresPermissions("admin:userFeedback:list")
    @GetMapping("/list")
    public TableDataInfo list(UserFeedBackDTO userFeedBackDTO)
    {
        Page<UserFeedbackVO> list = userFeedbackService.selectUserFeedbackByDTO(TableSupport.buildPageRequest().buildPage(), userFeedBackDTO);
        return getDataTable(list);
    }

    @RequiresPermissions("admin:userFeedback:query")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userFeedbackService.selectUserFeedbackById(id));
    }

    @RequiresPermissions("admin:userFeedback:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody UserFeedbackUpdateDTO userFeedbackUpdateDTO)
    {
        return AjaxResult.success(userFeedbackService.updateUserFeedback(userFeedbackUpdateDTO));
    }
}
