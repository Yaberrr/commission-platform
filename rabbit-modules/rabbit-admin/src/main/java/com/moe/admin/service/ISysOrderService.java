package com.moe.admin.service;

import com.moe.common.core.domain.dto.order.OrderListDTO;
import com.moe.common.core.domain.vo.order.OrderVO;

import java.util.List;

public interface ISysOrderService {

    List<OrderVO> selectOrder(OrderListDTO orderListDTO);


}
