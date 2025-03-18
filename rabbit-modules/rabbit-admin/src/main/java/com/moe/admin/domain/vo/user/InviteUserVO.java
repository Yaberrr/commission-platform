package com.moe.admin.domain.vo.user;

import com.moe.common.core.enums.user.Gender;
import lombok.Data;

@Data
public class InviteUserVO {

    private Long id;

    private String userName;

    private String wechat;

    private String phoneNumber;

    private Gender sex;

    private String area;
}
