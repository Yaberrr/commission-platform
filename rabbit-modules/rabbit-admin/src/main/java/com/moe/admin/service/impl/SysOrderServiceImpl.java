package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.mapper.OrderMapper;
import com.moe.admin.service.ISysOrderService;
import com.moe.admin.domain.dto.order.OrderListDTO;
import com.moe.admin.domain.vo.order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOrderServiceImpl implements ISysOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Page<OrderVO> selectOrder(IPage page, OrderListDTO orderListDTO) {
        return orderMapper.selectOrderVOByDTO(page, orderListDTO);
    }
}
