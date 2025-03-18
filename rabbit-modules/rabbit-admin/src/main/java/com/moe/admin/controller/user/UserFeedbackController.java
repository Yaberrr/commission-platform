package com.moe.admin.controller.user;

import com.moe.admin.service.UserFeedbackService;
import com.moe.common.core.domain.dto.user.UserFeedBackDTO;
import com.moe.common.core.domain.dto.user.UserFeedbackUpdateDTO;
import com.moe.common.core.domain.vo.user.UserFeedbackVO;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
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
        startPage();
        List<UserFeedbackVO> list = userFeedbackService.selectUserFeedbackByDTO(userFeedBackDTO);
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
