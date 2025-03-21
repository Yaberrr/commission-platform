package com.moe.common.core.domain.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.moe.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/3/20
 */
@Data
@TableName(value = "rb_product_group", autoResultMap = true)
public class ProductGroup extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    //组名
    private String groupName;

    //平台参数
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> platformDictIds;

}
