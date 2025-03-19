package com.moe.admin.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.user.UserMessageAddDTO;
import com.moe.admin.service.UserMessageService;
import com.moe.admin.domain.dto.user.UserMessageDTO;
import com.moe.admin.domain.vo.user.UserMessageVO;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userMessage")
public class UserMessageController extends BaseController {

    @Autowired
    private UserMessageService userMessageService;

    @RequiresPermissions("admin:userMessage:list")
    @GetMapping("/list")
    public TableDataInfo list(UserMessageDTO userMessageDTO)
    {
        Page<UserMessageVO> list = userMessageService.selectUserMessageVOByDTO(TableSupport.buildPageRequest().buildPage(), userMessageDTO);
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

    @RequiresPermissions("admin:userMessage:list")
    @GetMapping("/detail/{userId}")
    public AjaxResult detailByUserId(@PathVariable("userId") Long userId)
    {
        return AjaxResult.success(userMessageService.selectMessageVOByUserId(TableSupport.buildPageRequest().buildPage(), userId));
    }

    @RequiresPermissions("admin:userMessage:add")
    @PostMapping
    public AjaxResult add(@RequestBody UserMessageAddDTO userMessageAddDTO)
    {
        return AjaxResult.success(userMessageService.addUserMessageByDTO(userMessageAddDTO));
    }

    @RequiresPermissions("admin:userMessage:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody UserMessageAddDTO userMessageAddDTO)
    {
        return AjaxResult.success(userMessageService.udpateUserMessageByDTO(userMessageAddDTO));
    }
}
