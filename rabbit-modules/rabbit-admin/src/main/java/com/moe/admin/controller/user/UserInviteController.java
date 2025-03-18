package com.moe.admin.controller.user;

import com.moe.admin.service.UserInviteService;
import com.moe.admin.domain.dto.user.UserInviteDTO;
import com.moe.admin.domain.vo.user.InviteUserRankVO;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userInvite")
public class UserInviteController extends BaseController {

    @Autowired
    private UserInviteService userInviteService;

    @RequiresPermissions("admin:userInvite:list")
    @GetMapping("/list")
    public TableDataInfo list(UserInviteDTO userInviteDTO)
    {
        startPage();
        List<InviteUserRankVO> list = userInviteService.selectInviteUserRankVOByDTO(userInviteDTO);
        return getDataTable(list);
    }

    @RequiresPermissions("admin:userInvite:query")
    @GetMapping("/period")
    public AjaxResult periodDetail(UserInviteDTO userInviteDTO)
    {
        return AjaxResult.success(userInviteService.selectInviteUserVOByPeriod(userInviteDTO));
    }

}
