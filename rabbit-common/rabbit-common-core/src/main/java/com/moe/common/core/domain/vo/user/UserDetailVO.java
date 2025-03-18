package com.moe.common.core.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.annotation.Excel;
import com.moe.common.core.enums.user.Gender;
import com.moe.common.core.enums.user.MemberLevel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserDetailVO {

    private Long id;

    private String userName;

    private Gender sex;

    private String area;

    private String wechat;

    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    private MemberLevel memberLevel;

    private Integer orderCount;

    private BigDecimal validTransactionAmount;

    private BigDecimal validEarningsAmount;

    private BigDecimal PendingEarningsAmount;

    private BigDecimal withdrawalAmount;

    private Integer inviteCount;

    private Integer childInviteCount;

    private Integer inviteOrderCount;

    private Integer childInviteOrderCount;
}
