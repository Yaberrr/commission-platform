package com.moe.admin.mapper;

import com.moe.admin.domain.dto.order.OrderListDTO;
import com.moe.admin.domain.vo.order.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {


    public List<OrderVO> selectOrderVOByDTO(@Param("dto") OrderListDTO orderListDTO);
}
