package com.moe.admin.service;

import com.moe.admin.domain.dto.order.OrderListDTO;
import com.moe.admin.domain.vo.order.OrderVO;

import java.util.List;

public interface ISysOrderService {

    List<OrderVO> selectOrder(OrderListDTO orderListDTO);


}
