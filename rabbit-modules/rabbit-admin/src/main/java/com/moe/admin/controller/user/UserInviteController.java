package com.moe.admin.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.service.IUserInviteService;
import com.moe.admin.domain.dto.user.UserInviteDTO;
import com.moe.admin.domain.vo.user.InviteUserRankVO;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userInvite")
public class UserInviteController extends BaseController {

    @Autowired
    private IUserInviteService userInviteService;

    @RequiresPermissions("admin:userInvite:list")
    @GetMapping("/list")
    public TableDataInfo list(UserInviteDTO userInviteDTO)
    {
        Page<InviteUserRankVO> list = userInviteService.selectInviteUserRankVOByDTO(TableSupport.buildPageRequest().buildPage(), userInviteDTO);
        return getDataTable(list);
    }

    @RequiresPermissions("admin:userInvite:query")
    @GetMapping("/period")
    public AjaxResult periodDetail(UserInviteDTO userInviteDTO)
    {
        return AjaxResult.success(userInviteService.selectInviteUserVOByPeriod(userInviteDTO));
    }

}
