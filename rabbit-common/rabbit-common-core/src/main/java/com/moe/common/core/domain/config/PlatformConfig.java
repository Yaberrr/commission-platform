package com.moe.common.core.domain.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moe.common.core.enums.config.PlatformConfigType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 平台配置
 */
@Data
@TableName("rb_platform_config")
public class PlatformConfig extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 平台类型
    private PlatformType platformType;

    // 配置类型 1返佣比例
    private PlatformConfigType configType;

    // 配置内容
    private String configJson;

    /**
     * 返佣比例
     */
    @Data
    public static class CommissionRatio {

        //等级
        private List<Level> levelList;

        @Data
        public static class Level {
            //会员等级
            private MemberLevel level;

            //总收益
            private BigDecimal totalRate;

            //一级收益
            private BigDecimal selfRate;

            //二级收益
            private BigDecimal parentRate;

            //三级收益
            private BigDecimal grandParentRate;

        }
    }
}
