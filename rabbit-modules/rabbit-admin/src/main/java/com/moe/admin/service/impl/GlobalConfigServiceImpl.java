package com.moe.admin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moe.admin.mapper.GlobalConfigMapper;
import com.moe.admin.service.GlobalConfigService;
import com.moe.common.core.domain.config.GlobalConfig;
import com.moe.common.core.domain.dto.rule.GlobalConfigDTO;
import com.moe.common.core.domain.dto.rule.InviteModelDTO;
import com.moe.common.core.domain.dto.rule.QRCodeDTO;
import com.moe.common.core.domain.dto.user.MemberShipDTO;
import com.moe.common.core.domain.vo.rule.GlobalConfigVO;
import com.moe.common.core.domain.vo.rule.InviteModelVO;
import com.moe.common.core.domain.vo.rule.QRCodeVO;
import com.moe.common.core.domain.vo.user.MemberShipVO;
import com.moe.common.core.enums.config.GlobalConfigType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalConfigServiceImpl implements GlobalConfigService {

    @Autowired
    private GlobalConfigMapper globalConfigMapper;

    @Override
    public MemberShipVO selectMemberShip() {
        GlobalConfigVO globalConfigVO = globalConfigMapper.selectGlobalConfigByConfigType(GlobalConfigType.MEMBER_GROWTH.getCode());
        if (globalConfigVO == null || globalConfigVO.getConfigJson() == null) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MemberShipVO memberShipVO = objectMapper.readValue(globalConfigVO.getConfigJson(), MemberShipVO.class);
            memberShipVO.setId(globalConfigVO.getId());
            return memberShipVO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addOrUpdateMemberShip(MemberShipDTO memberShipDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setConfigType(GlobalConfigType.MEMBER_GROWTH);
        try {
            globalConfig.setConfigJson(objectMapper.writeValueAsString(memberShipDTO));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (memberShipDTO.getId() != null){
            globalConfig.setId(memberShipDTO.getId());
            return globalConfigMapper.updateById(globalConfig);
        }else {
            return globalConfigMapper.insert(globalConfig);
        }
    }

    @Override
    public QRCodeVO selectQRCode() {
        GlobalConfigVO globalConfigVO = globalConfigMapper.selectGlobalConfigByConfigType(GlobalConfigType.CUSTOMER_QR_CODE.getCode());
        if (globalConfigVO == null || globalConfigVO.getConfigJson() == null) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            QRCodeVO qrCodeVO = objectMapper.readValue(globalConfigVO.getConfigJson(), QRCodeVO.class);
            qrCodeVO.setId(globalConfigVO.getId());
            return qrCodeVO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addOrUpdateQRCode(QRCodeDTO qrCodeDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setConfigType(GlobalConfigType.CUSTOMER_QR_CODE);
        try {
            globalConfig.setConfigJson(objectMapper.writeValueAsString(qrCodeDTO));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (qrCodeDTO.getId() != null){
            globalConfig.setId(qrCodeDTO.getId());
            return globalConfigMapper.updateById(globalConfig);
        }else {
            return globalConfigMapper.insert(globalConfig);
        }
    }

    @Override
    public InviteModelVO selectInviteModel() {
        GlobalConfigVO globalConfigVO = globalConfigMapper.selectGlobalConfigByConfigType(GlobalConfigType.INVITATION_TEMPLATE.getCode());
        if (globalConfigVO == null || globalConfigVO.getConfigJson() == null) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InviteModelVO inviteModelVO = objectMapper.readValue(globalConfigVO.getConfigJson(), InviteModelVO.class);
            inviteModelVO.setId(globalConfigVO.getId());
            return inviteModelVO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addOrUpdateInviteModel(InviteModelDTO inviteModelDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setConfigType(GlobalConfigType.INVITATION_TEMPLATE);
        try {
            globalConfig.setConfigJson(objectMapper.writeValueAsString(inviteModelDTO));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (inviteModelDTO.getId() != null){
            globalConfig.setId(inviteModelDTO.getId());
            return globalConfigMapper.updateById(globalConfig);
        }else {
            return globalConfigMapper.insert(globalConfig);
        }
    }

}
