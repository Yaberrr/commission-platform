package com.moe.admin.mapper;

import com.moe.common.core.domain.dto.order.OrderListDTO;
import com.moe.common.core.domain.vo.order.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {


    public List<OrderVO> selectOrderVOByDTO(@Param("dto") OrderListDTO orderListDTO);
}
