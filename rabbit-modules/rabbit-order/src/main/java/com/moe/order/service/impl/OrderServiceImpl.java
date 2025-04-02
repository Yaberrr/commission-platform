package com.moe.order.service.impl;

import com.moe.common.core.domain.order.Order;
import com.moe.order.mapper.OrderMapper;
import com.moe.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/2
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public int updateOrder(List<Order> orderList) {

        return orderList.size();
    }

}
