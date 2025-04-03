package com.moe.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.utils.Assert;
import com.moe.order.domain.bo.OrderStatusBO;
import com.moe.order.dto.BatchUpdateOrderDTO;
import com.moe.order.handler.OrderHandlerFactory;
import com.moe.order.mapper.OrderMapper;
import com.moe.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tangyabo
 * @date 2025/4/2
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderHandlerFactory orderHandlerFactory;

    @Override
    @Transactional
    public int batchUpdateOrder(BatchUpdateOrderDTO dto) {
        //查询已存在的订单
        Map<String, Order> existMap = orderMapper.selectList(new LambdaQueryWrapper<Order>()
                        .in(Order::getPlatformOrderId, dto.getOrderList().stream().map(Order::getPlatformOrderId).collect(Collectors.toSet()))
                        .eq(Order::getPlatformType, dto.getPlatformType()))
                .stream().collect(Collectors.toMap(Order::getPlatformOrderId, order -> order));

        //新增订单
        List<Order> newList = new ArrayList<>();
        //需更新订单
        List<Order> updateList = new ArrayList<>();
        //状态变化
        List<OrderStatusBO> statusList = new ArrayList<>();
        for (Order newBean : dto.getOrderList()) {
            Order oldBean = existMap.get(newBean.getPlatformOrderId());
            if(oldBean == null){
                //新增
                newList.add(newBean);
                //状态变化
                OrderStatusBO statusBO = new OrderStatusBO(newBean, OrderStatus.NEW, newBean.getStatus());
                statusList.add(statusBO);
            }else{
                if(!newBean.getStatus().equals(oldBean.getStatus())) {
                    //仅更新状态
                    Order updateBean = new Order();
                    updateBean.setId(oldBean.getId());
                    updateBean.setStatus(newBean.getStatus());
                    updateList.add(updateBean);
                    //状态变化
                    OrderStatusBO statusBO = new OrderStatusBO(oldBean,oldBean.getStatus(),newBean.getStatus());
                    statusList.add(statusBO);
                }
            }
        }
        //批量新增
        if(CollUtil.isNotEmpty(newList)) {
            Assert.isTrue(orderMapper.insertBatch(newList), "订单新增失败");
        }
        //批量更新
        if(CollUtil.isNotEmpty(updateList)) {
            Assert.isTrue(orderMapper.updateBatchById(updateList), "订单更新失败");
        }

        int orderCount = dto.getOrderList().size();
        for (OrderStatusBO statusBO : statusList) {
            //处理状态变化
            boolean isSuccess = orderHandlerFactory.changeStatus(statusBO);
            if(!isSuccess){
                //回退状态
                Order rollBackBean = new Order();
                rollBackBean.setId(statusBO.getOrder().getId());
                rollBackBean.setStatus(statusBO.getOldStatus());
                orderMapper.updateById(rollBackBean);
                orderCount-=1;
            }
        }
        return orderCount;
    }


}
