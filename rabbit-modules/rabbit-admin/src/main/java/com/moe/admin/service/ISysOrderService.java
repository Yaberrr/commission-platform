package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.order.OrderListDTO;
import com.moe.admin.domain.vo.order.OrderVO;

import java.util.List;

public interface ISysOrderService {

    Page<OrderVO> selectOrder(IPage page, OrderListDTO orderListDTO);


}
