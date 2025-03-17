package com.moe.admin.controller.user;

import com.moe.admin.service.UserMessageService;
import com.moe.common.core.domain.dto.user.UserFeedBackDTO;
import com.moe.common.core.domain.dto.user.UserMessageDTO;
import com.moe.common.core.domain.vo.user.UserFeedbackVO;
import com.moe.common.core.domain.vo.user.UserMessageVO;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/userMessage")
public class UserMessageController extends BaseController {

    @Autowired
    private UserMessageService userMessageService;

    @RequiresPermissions("admin:userMessage:list")
    @GetMapping("/list")
    public TableDataInfo list(UserMessageDTO userMessageDTO)
    {
        startPage();
        List<UserMessageVO> list = userMessageService.selectUserMessageVOByDTO(userMessageDTO);
        return getDataTable(list);
    }

    @RequiresPermissions("admin:userMessage:query")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userMessageService.selectUserMessageDetailVOById(id));
    }

    @RequiresPermissions("admin:userMessage:remove")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userMessageService.deleteUserMessageById(id));
    }
}
