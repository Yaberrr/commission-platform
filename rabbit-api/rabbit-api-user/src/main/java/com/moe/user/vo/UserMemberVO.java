package com.moe.user.vo;

import com.moe.common.core.enums.user.MemberLevel;
import lombok.Data;

/**
 * 用户会员信息
 * @author tangyabo
 * @date 2025/4/3
 */
@Data
public class UserMemberVO {

    private Long userId;

    // 会员等级 1铜元宝 2银元宝 3金元宝
    private MemberLevel memberLevel;

    // 上游id
    private Long parentId;

    // 上上游id
    private Long grandparentId;

}
