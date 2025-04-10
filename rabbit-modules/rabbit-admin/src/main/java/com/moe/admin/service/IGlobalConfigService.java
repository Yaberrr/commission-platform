package com.moe.admin.service;

import com.moe.admin.domain.dto.rule.InviteTemplateDTO;
import com.moe.admin.domain.dto.rule.QRCodeDTO;
import com.moe.admin.domain.dto.user.MemberShipDTO;
import com.moe.admin.domain.vo.rule.InviteTemplateVO;
import com.moe.admin.domain.vo.rule.QRCodeVO;
import com.moe.admin.domain.vo.user.MemberShipVO;

public interface IGlobalConfigService {

    MemberShipVO selectMemberShip();

    int addOrUpdateMemberShip(MemberShipDTO memberShipDTO);

    QRCodeVO selectQRCode();

    int addOrUpdateQRCode(QRCodeDTO qrCodeDTO);

    InviteTemplateVO selectInviteModel();

    int addOrUpdateInviteTemplate(InviteTemplateDTO dto);
}
