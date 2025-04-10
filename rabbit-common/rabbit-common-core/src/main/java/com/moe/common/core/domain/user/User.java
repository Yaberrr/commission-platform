package com.moe.common.core.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moe.common.core.enums.config.UserConfigType;
import com.moe.common.core.enums.user.Gender;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.vo.PairVO;
import com.moe.common.core.web.domain.BaseEntity;
import javafx.util.Pair;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户
 */
@Data
@TableName("rb_user")
public class User extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户名
    private String userName;

    //手机号
    private String phoneNumber;

    //元宝号
    private String ybCode;

    //微信唯一标识
    private String wechatOpenId;

    // 微信号
    private String wechat;

    // 地区
    private String area;

    // 性别（0男 1女 2未知）
    private Gender sex;

    // 会员等级 1铜元宝 2银元宝 3金元宝
    private MemberLevel memberLevel;

    // 邀请数量
    private int inviteCount;

    // 下游邀请数量
    private int childInviteCount;

    // 上游id
    private Long parentId;

    // 上上游id
    private Long grandparentId;

    // 分享商品次数
    private int shareCount;

    // 分享商品被点击次数
    private int shareHitCount;

    // 注册时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    // 最后登录时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    //用户头像
    private String avatarUrl;

    //用户配置列表
    @TableField(exist = false)
    private List<PairVO<Integer,String>> configList;

    public String getUserConfigValue(UserConfigType type) {
        return configList.stream().filter(c -> c.getKey().equals(type.getCode())).findFirst()
                .orElseThrow(() -> new ServiceException("对应用户配置不存在")).getValue();
    }

}
