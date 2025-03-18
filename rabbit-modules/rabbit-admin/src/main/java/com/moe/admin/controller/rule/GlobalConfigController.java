package com.moe.admin.controller.rule;

import com.moe.admin.service.GlobalConfigService;
import com.moe.common.core.domain.dto.rule.GlobalConfigDTO;
import com.moe.common.core.domain.dto.rule.InviteModelDTO;
import com.moe.common.core.domain.dto.rule.QRCodeDTO;
import com.moe.common.core.domain.dto.user.MemberShipDTO;
import com.moe.common.core.domain.dto.user.UserFeedBackDTO;
import com.moe.common.core.domain.vo.user.UserFeedbackVO;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/globalConfig")
public class GlobalConfigController {

    @Autowired
    private GlobalConfigService globalConfigService;

    @RequiresPermissions("admin:globalConfig:memberShip")
    @GetMapping("/memberShip")
    public AjaxResult memberShip()
    {
        return AjaxResult.success(globalConfigService.selectMemberShip());
    }

    @RequiresPermissions("admin:globalConfig:editMemberShip")
    @PostMapping("/memberShip")
    public AjaxResult editMemberShip(@RequestBody MemberShipDTO memberShipDTO)
    {
        return AjaxResult.success(globalConfigService.addOrUpdateMemberShip(memberShipDTO));
    }

    @RequiresPermissions("admin:globalConfig:QRCode")
    @GetMapping("/QRCode")
    public AjaxResult QRCode()
    {
        return AjaxResult.success(globalConfigService.selectQRCode());
    }

    @RequiresPermissions("admin:globalConfig:editQRCode")
    @PostMapping("/QRCode")
    public AjaxResult editQRCode(@RequestBody QRCodeDTO qrCodeDTO)
    {
        return AjaxResult.success(globalConfigService.addOrUpdateQRCode(qrCodeDTO));
    }

    @RequiresPermissions("admin:globalConfig:inviteModel")
    @GetMapping("/InviteMode")
    public AjaxResult InviteModel()
    {
        return AjaxResult.success(globalConfigService.selectInviteModel());
    }

    @RequiresPermissions("admin:globalConfig:editInviteMode")
    @PostMapping("/InviteMode")
    public AjaxResult editInviteMode(@RequestBody InviteModelDTO inviteModelDTO)
    {
        return AjaxResult.success(globalConfigService.addOrUpdateInviteModel(inviteModelDTO));
    }

}
