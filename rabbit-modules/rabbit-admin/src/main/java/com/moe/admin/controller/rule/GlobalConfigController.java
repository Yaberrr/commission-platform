package com.moe.admin.controller.rule;

import com.moe.admin.domain.dto.rule.InviteTemplateDTO;
import com.moe.admin.domain.dto.rule.QRCodeDTO;
import com.moe.admin.domain.dto.user.MemberShipDTO;
import com.moe.admin.service.IGlobalConfigService;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/globalConfig")
public class GlobalConfigController {

    @Autowired
    private IGlobalConfigService globalConfigService;

    @RequiresPermissions("admin:globalConfig:memberShip")
    @GetMapping("/memberShip")
    public AjaxResult memberShip() {
        return AjaxResult.success(globalConfigService.selectMemberShip());
    }

    @RequiresPermissions("admin:globalConfig:editMemberShip")
    @PostMapping("/memberShip")
    public AjaxResult editMemberShip(@RequestBody MemberShipDTO memberShipDTO) {
        return AjaxResult.success(globalConfigService.addOrUpdateMemberShip(memberShipDTO));
    }

    @RequiresPermissions("admin:globalConfig:QRCode")
    @GetMapping("/QRCode")
    public AjaxResult QRCode() {
        return AjaxResult.success(globalConfigService.selectQRCode());
    }

    @RequiresPermissions("admin:globalConfig:editQRCode")
    @PostMapping("/QRCode")
    public AjaxResult editQRCode(@RequestBody QRCodeDTO qrCodeDTO) {
        return AjaxResult.success(globalConfigService.addOrUpdateQRCode(qrCodeDTO));
    }

    @RequiresPermissions("admin:globalConfig:inviteTemplate")
    @GetMapping("/inviteTemplate")
    public AjaxResult getInviteTemplate() {
        return AjaxResult.success(globalConfigService.selectInviteModel());
    }

    @RequiresPermissions("admin:globalConfig:editInviteTemplate")
    @PostMapping("/inviteTemplate")
    public AjaxResult editInviteTemplate(@RequestBody InviteTemplateDTO inviteModelDTO) {
        return AjaxResult.success(globalConfigService.addOrUpdateInviteTemplate(inviteModelDTO));
    }

}
