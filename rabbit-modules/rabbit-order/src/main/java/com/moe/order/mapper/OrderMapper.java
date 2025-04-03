package com.moe.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.common.core.domain.order.Order;
import com.moe.common.core.mapper.BaseMapperPlus;
import com.moe.order.domain.dto.OrderListDTO;
import com.moe.order.domain.vo.OrderListVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author tangyabo
 * @date 2025/4/2
 */
public interface OrderMapper extends BaseMapperPlus<OrderMapper, Order, Order> {

    /**
     * 查询订单列表
     * @param page   分页
     * @param dto    查询条件
     * @param userId 用户id
     * @return
     */
    Page<OrderListVO> orderList(@Param("page") IPage page, @Param("dto") OrderListDTO dto, @Param("userId") Long userId);
}
