package com.moe.admin.service;

import com.moe.common.core.domain.dto.rule.InviteModelDTO;
import com.moe.common.core.domain.dto.rule.QRCodeDTO;
import com.moe.common.core.domain.dto.user.MemberShipDTO;
import com.moe.common.core.domain.vo.rule.InviteModelVO;
import com.moe.common.core.domain.vo.rule.QRCodeVO;
import com.moe.common.core.domain.vo.user.MemberShipVO;

public interface GlobalConfigService {

    MemberShipVO selectMemberShip();

    int addOrUpdateMemberShip(MemberShipDTO memberShipDTO);

    QRCodeVO selectQRCode();

    int addOrUpdateQRCode(QRCodeDTO qrCodeDTO);

    InviteModelVO selectInviteModel();

    int addOrUpdateInviteModel(InviteModelDTO inviteModelDTO);
}
