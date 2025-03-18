package com.moe.common.core.domain.dto.user;

import com.moe.common.core.enums.user.MemberLevel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDTO {

    private String id;

    private String userName;

    private String phoneNumber;

    private String wechat;

    private MemberLevel memberLevel;

    private String minRegisterTime;

    private String maxRegisterTime;

    private String minLastLoginTime;

    private String maxLastLoginTime;

    private Integer minOrderCount;

    private Integer maxOrderCount;

    private BigDecimal minOrderAmount;

    private BigDecimal maxOrderAmount;

    private BigDecimal minEarnAmount;

    private BigDecimal maxEarnAmount;

    private BigDecimal minWithdrawalAmount;

    private BigDecimal maxWithdrawalAmount;

    private Integer minInviteCount;

    private Integer maxInviteCount;
}
