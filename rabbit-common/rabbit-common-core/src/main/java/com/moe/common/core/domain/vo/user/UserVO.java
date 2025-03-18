package com.moe.common.core.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.enums.user.Gender;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserVO {

    @Excel(name = "用户ID")
    private Long id;

    @Excel(name = "用户名")
    private String userName;

    @Excel(name = "性别")
    private Gender sex;

    @Excel(name = "地区")
    private String area;

    @Excel(name = "微信号")
    private String wechat;

    @Excel(name = "手机号")
    private String phoneNumber;

    @Excel(name = "会员等级")
    private MemberLevel memberLevel;

    @Excel(name = "有效订单笔数")
    private Integer orderCount;

    @Excel(name = "有效交易金额")
    private BigDecimal validTransactionAmount;

    @Excel(name = "有效收益金额")
    private BigDecimal validEarningsAmount;

    @Excel(name = "即将到账收益金额")
    private BigDecimal PendingEarningsAmount;

    @Excel(name = "已提现金额")
    private BigDecimal withdrawalAmount;

    @Excel(name = "分享好友次数")
    private Integer shareCount;

    @Excel(name = "分享好友成交次数")
    private Integer shareHitCount;

    @Excel(name = "邀请好友数")
    private Integer inviteCount;

    @Excel(name = "下级邀请好友数")
    private Integer childInviteCount;

    @Excel(name = "邀请好友下单数")
    private Integer inviteOrderCount;

    @Excel(name = "下级邀请下单数")
    private Integer childInviteOrderCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注册时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后登录时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    @Excel(name = "邀请码")
    private String inviteCode;
}
