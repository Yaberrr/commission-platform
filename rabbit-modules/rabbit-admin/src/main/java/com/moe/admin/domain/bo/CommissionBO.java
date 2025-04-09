package com.moe.admin.domain.bo;

import com.moe.admin.domain.dto.user.MemberShipDTO;
import lombok.Data;

import java.util.List;

/**
 * 平台佣金配置
 *
 * @author liang.lu
 * 2025/4/9 18:48:16
 */
@Data
public class CommissionBO {
    private Long id;

    private Integer platformType;

    private Integer configType;

    private List<MemberShipDTO.Commission.Level> levelList;
}
