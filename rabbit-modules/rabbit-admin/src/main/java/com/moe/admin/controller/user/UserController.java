package com.moe.admin.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.service.IUserService;
import com.moe.admin.domain.dto.user.UserDTO;
import com.moe.admin.domain.vo.user.UserVO;
import com.moe.common.core.utils.poi.ExcelUtil;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/adminUser")
public class
UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequiresPermissions("admin:user:list")
    @GetMapping("/list")
    public TableDataInfo list(UserDTO userDTO)
    {
        Page<UserVO> list = userService.selectUserVOByDTO(TableSupport.buildPageRequest().buildPage(), userDTO);
        return getDataTable(list);
    }

    @RequiresPermissions("admin:user:list")
    @GetMapping("/inviteUser")
    public AjaxResult inviteUser(UserDTO userDTO)
    {
        return AjaxResult.success(userService.selectInviteUserVOByDTO(userDTO));
    }

    @RequiresPermissions("admin:user:list")
    @GetMapping("/grandInviteUser")
    public AjaxResult grandInviteUser(UserDTO userDTO)
    {
        return AjaxResult.success(userService.selectGrandInviteUserVOByDTO(userDTO));
    }

    @RequiresPermissions("admin:user:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserDTO userDTO)
    {
        Page page = new Page<>(1, 20000);
        Page<UserVO> list = userService.selectUserVOByDTO(page, userDTO);
        ExcelUtil<UserVO> util = new ExcelUtil<UserVO>(UserVO.class);
        util.exportExcel(response, list.getRecords(), "用户表");
    }

    @RequiresPermissions("admin:user:query")
    @GetMapping("/{id}")
    public AjaxResult getDetail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userService.selectUserDetailByUserId(id));
    }

    @RequiresPermissions("admin:user:list")
    @GetMapping("/userSearch")
    public AjaxResult menu(UserDTO userDTO)
    {
        return AjaxResult.success(userService.selectUserListVO(userDTO));
    }
}
