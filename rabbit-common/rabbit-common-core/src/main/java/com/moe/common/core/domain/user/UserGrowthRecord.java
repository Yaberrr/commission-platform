package com.moe.common.core.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 用户成长记录
 */
@Data
@TableName("rb_user_growth_record")
public class UserGrowthRecord extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户id
    private Long userId;

    // 事件类型
    private String eventType;

    // 成长值
    private Integer growthValue;

    // 状态 0失效 1有效
    private Integer status;

    // 订单id
    private Long orderId;

    // 订单编号
    private String orderNumber;
}
