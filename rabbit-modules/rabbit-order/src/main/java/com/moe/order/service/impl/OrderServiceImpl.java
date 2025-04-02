package com.moe.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.utils.Assert;
import com.moe.order.domain.bo.OrderStatusBO;
import com.moe.order.mapper.OrderMapper;
import com.moe.order.service.IOrderCommissionService;
import com.moe.order.service.IOrderService;
import javafx.util.Pair;
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
    private IOrderCommissionService orderCommissionService;

    @Override
    @Transactional
    public int batchUpdateOrder(PlatformType platformType, List<Order> orderList) {
        //查询已存在的订单
        Map<String, Order> existMap = orderMapper.selectList(new LambdaQueryWrapper<Order>()
                        .in(Order::getPlatformNo, orderList.stream().map(Order::getPlatformNo).collect(Collectors.toSet()))
                        .eq(Order::getPlatformType, platformType))
                .stream().collect(Collectors.toMap(Order::getPlatformNo, order -> order));

        //新增订单
        List<Order> newList = new ArrayList<>();
        //需更新订单
        List<Order> updateList = new ArrayList<>();
        //状态变化
        List<OrderStatusBO> statusList = new ArrayList<>();
        for (Order newBean : orderList) {
            Order oldBean = existMap.get(newBean.getPlatformNo());
            if(oldBean == null){
                //新增
                newBean.setOrderNo("  ");
                newList.add(newBean);
                //状态变化
                OrderStatusBO statusBO = new OrderStatusBO(newBean, null,newBean.getStatus());
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
        Assert.isTrue(orderMapper.insertBatch(newList), "订单新增失败");
        //批量更新
        Assert.isTrue(orderMapper.updateBatchById(updateList), "订单更新失败");

        //处理状态变化对应的佣金
        orderCommissionService.handleCommission(statusList);
        return orderList.size();
    }


}
