package com.moe.admin.controller.user;

import com.moe.admin.service.UserService;
import com.moe.admin.domain.dto.user.UserDTO;
import com.moe.admin.domain.vo.user.UserVO;
import com.moe.common.core.utils.poi.ExcelUtil;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequiresPermissions("admin:user:list")
    @GetMapping("/list")
    public TableDataInfo list(UserDTO userDTO)
    {
        startPage();
        List<UserVO> list = userService.selectUserVOByDTO(userDTO);
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
    @GetMapping("/export")
    public void export(HttpServletResponse response, UserDTO userDTO)
    {
        List<UserVO> list = userService.selectUserVOByDTO(userDTO);
        ExcelUtil<UserVO> util = new ExcelUtil<UserVO>(UserVO.class);
        util.exportExcel(response, list, "用户表");
    }

    @RequiresPermissions("admin:user:query")
    @GetMapping("/{id}")
    public AjaxResult getDetail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userService.selectUserDetailByUserId(id));
    }
}
