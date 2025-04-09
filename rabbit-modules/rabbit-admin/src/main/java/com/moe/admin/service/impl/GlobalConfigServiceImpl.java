package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moe.admin.domain.bo.CommissionBO;
import com.moe.admin.domain.bo.MemberUpdateBO;
import com.moe.admin.domain.dto.rule.InviteTemplateDTO;
import com.moe.admin.domain.dto.rule.QRCodeDTO;
import com.moe.admin.domain.dto.user.MemberShipDTO;
import com.moe.admin.domain.vo.rule.GlobalConfigVO;
import com.moe.admin.domain.vo.rule.InviteTemplateVO;
import com.moe.admin.domain.vo.rule.QRCodeVO;
import com.moe.admin.domain.vo.user.MemberShipVO;
import com.moe.admin.mapper.GlobalConfigMapper;
import com.moe.admin.service.IGlobalConfigService;
import com.moe.common.core.domain.config.GlobalConfig;
import com.moe.common.core.domain.config.PlatformConfig;
import com.moe.common.core.enums.config.GlobalConfigType;
import com.moe.common.core.enums.config.PlatformConfigType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.module.mapper.PlatformConfigMapper;
import com.moe.common.module.service.PlatformConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GlobalConfigServiceImpl implements IGlobalConfigService {

    @Autowired
    private GlobalConfigMapper globalConfigMapper;

    @Autowired
    private PlatformConfigMapper platformConfigMapper;

    @Autowired
    private PlatformConfigService platformConfigService;

    @Override
    public MemberShipVO selectMemberShip() {
        try {
            // 获取会员升级信息
            MemberShipVO memberShipVO = new MemberShipVO();
            GlobalConfigVO globalConfigVO = globalConfigMapper.selectGlobalConfigByConfigType(GlobalConfigType.MEMBER_GROWTH.getCode());
            if (globalConfigVO == null || globalConfigVO.getConfigJson() == null) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            MemberUpdateBO memberUpdateBO = objectMapper.readValue(globalConfigVO.getConfigJson(), MemberUpdateBO.class);
            memberShipVO.setLevelUpdate(memberUpdateBO);

            // 获取会员佣金信息
            List<PlatformConfig> platformConfigList = platformConfigMapper.selectList(new QueryWrapper<PlatformConfig>().eq("config_type", PlatformConfigType.COMMISSION_RATIO.getCode()));
            if (!platformConfigList.isEmpty()) {
                List<CommissionBO> commissionList = platformConfigList.stream().map(platformConfig -> {
                    CommissionBO commission = new CommissionBO();
                    commission.setPlatformType(platformConfig.getPlatformType().getCode());
                    commission.setConfigType(platformConfig.getConfigType().getCode());
                    try {
                        commission.setLevelList(objectMapper.readValue(platformConfig.getConfigJson(), List.class));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return commission;
                }).collect(Collectors.toList());  // 添加collect终止操作
                memberShipVO.setCommissionList(commissionList);  // 将结果设置到VO中
            }

            return memberShipVO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addOrUpdateMemberShip(MemberShipDTO memberShipDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            GlobalConfig globalConfig = new GlobalConfig();

            // 保存会员升级配置
            globalConfig.setConfigType(GlobalConfigType.MEMBER_GROWTH);
            MemberUpdateBO memberUpdateBO = new MemberUpdateBO();
            memberUpdateBO.setConditionList(memberShipDTO.getConditionList());
            memberUpdateBO.setReward(memberShipDTO.getReward());
            globalConfig.setConfigJson(objectMapper.writeValueAsString(memberUpdateBO));

            QueryWrapper<GlobalConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("config_type", GlobalConfigType.MEMBER_GROWTH.getCode());
            List<GlobalConfig> globalConfigList = globalConfigMapper.selectList(queryWrapper);
            if (!globalConfigList.isEmpty()) {
                globalConfig.setId(globalConfigList.get(0).getId());
                globalConfigMapper.updateById(globalConfig);
            } else {
                globalConfigMapper.insert(globalConfig);
            }
            // 保存会员佣金配置
            if (memberShipDTO.getCommissionList() != null) {
                saveCommissionConfig(memberShipDTO.getCommissionList(), objectMapper);
            }
            return 1;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 保存会员佣金配置
    private void saveCommissionConfig(List<MemberShipDTO.Commission> commissionList, ObjectMapper objectMapper) {
        commissionList.forEach(commission -> {
            //检测平台类型是否存在
            Integer platformType = commission.getPlatformType();
            if (PlatformType.fromCode(platformType) == null) {
                throw new RuntimeException("平台类型不存在");
            }

            PlatformConfig platformConfig = new PlatformConfig();
            platformConfig.setPlatformType(PlatformType.fromCode(platformType));
            platformConfig.setConfigType(PlatformConfigType.COMMISSION_RATIO);
            if (commission.getLevelList() != null) {
                try {
                    platformConfig.setConfigJson(objectMapper.writeValueAsString(commission.getLevelList()));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            QueryWrapper<PlatformConfig> platformConfigQueryWrapper = new QueryWrapper<>();
            platformConfigQueryWrapper.eq("platform_type", platformType);
            platformConfigQueryWrapper.eq("config_type", PlatformConfigType.COMMISSION_RATIO.getCode());
            List<PlatformConfig> platformConfigList = platformConfigMapper.selectList(platformConfigQueryWrapper);
            if (!platformConfigList.isEmpty()) {
                platformConfig.setId(platformConfigList.get(0).getId());
                platformConfigMapper.updateById(platformConfig);
            } else {
                platformConfigMapper.insert(platformConfig);
            }
            //更新redis缓存
            platformConfigService.setConfig(PlatformType.fromCode(platformType), PlatformConfigType.COMMISSION_RATIO, platformConfig.getConfigJson());
        });
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
        if (qrCodeDTO.getId() != null) {
            globalConfig.setId(qrCodeDTO.getId());
            return globalConfigMapper.updateById(globalConfig);
        } else {
            return globalConfigMapper.insert(globalConfig);
        }
    }

    @Override
    public InviteTemplateVO selectInviteModel() {
        GlobalConfigVO globalConfigVO = globalConfigMapper.selectGlobalConfigByConfigType(GlobalConfigType.INVITATION_TEMPLATE.getCode());
        if (globalConfigVO == null || globalConfigVO.getConfigJson() == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InviteTemplateVO inviteModelVO = objectMapper.readValue(globalConfigVO.getConfigJson(), InviteTemplateVO.class);
            inviteModelVO.setId(globalConfigVO.getId());
            return inviteModelVO;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int addOrUpdateInviteTemplate(InviteTemplateDTO dto) {
        ObjectMapper objectMapper = new ObjectMapper();
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setConfigType(GlobalConfigType.INVITATION_TEMPLATE);
        try {
            globalConfig.setConfigJson(objectMapper.writeValueAsString(dto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (dto.getId() != null) {
            globalConfig.setId(dto.getId());
            return globalConfigMapper.updateById(globalConfig);
        } else {
            QueryWrapper<GlobalConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("config_type", GlobalConfigType.INVITATION_TEMPLATE.getCode());
            List<GlobalConfig> globalConfigs = globalConfigMapper.selectList(queryWrapper);
            if (!globalConfigs.isEmpty()) {
                globalConfig.setId(globalConfigs.get(0).getId());
            }
            return globalConfigMapper.insert(globalConfig);
        }
    }

}
