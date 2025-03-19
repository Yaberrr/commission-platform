package com.moe.admin.domain.dto.user;

import com.moe.common.core.enums.message.MessageContentType;
import com.moe.common.core.enums.message.MessageSendType;
import lombok.Data;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

@Data
public class UserMessageAddDTO {

    private Long id;

    private String title;

    private String content;

    private Integer contentForm;

    private String cover;

    private String sendTime;

    private Integer sendType;

    private List<Long> sendUser;

    private Integer status;
}
