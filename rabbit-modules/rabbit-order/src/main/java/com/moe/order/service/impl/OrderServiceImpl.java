package com.moe.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.common.core.domain.config.PlatformConfig;
import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.config.PlatformConfigType;
import com.moe.common.core.enums.order.OrderEvent;
import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.utils.Assert;
import com.moe.common.module.domain.bo.OrderEventBO;
import com.moe.common.module.service.PlatformConfigService;
import com.moe.common.module.utils.CommissionUtils;
import com.moe.common.mq.MqTopicConstant;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.order.domain.dto.OrderListDTO;
import com.moe.order.domain.vo.OrderListVO;
import com.moe.order.dto.BatchUpdateOrderDTO;
import com.moe.order.mapper.OrderMapper;
import com.moe.order.service.IOrderService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
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

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PlatformConfigService platformConfigService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

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
        //订单事件
        List<OrderEventBO> eventList = new ArrayList<>();
        for (Order newBean : dto.getOrderList()) {
            Order oldBean = existMap.get(newBean.getPlatformOrderId());
            if(oldBean == null){
                //新增
                newList.add(newBean);
                //状态变化
                OrderEvent event = OrderEvent.getEvent(null, newBean.getStatus());
                eventList.add(new OrderEventBO(newBean, event));
            }else{
                if(!newBean.getStatus().equals(oldBean.getStatus())) {
                    //仅更新状态和失败原因
                    Order updateBean = new Order();
                    updateBean.setId(oldBean.getId());
                    updateBean.setStatus(newBean.getStatus());
                    updateBean.setFailReason(newBean.getFailReason());
                    updateList.add(updateBean);
                    //状态变化
                    OrderEvent event = OrderEvent.getEvent(oldBean.getStatus(), newBean.getStatus());
                    if(event != null){
                        eventList.add(new OrderEventBO(oldBean, event));
                    }else{
                        log.warn("订单{}出现预料外的状态变化:{}到{}",oldBean.getId(),oldBean.getStatus().getDesc(),newBean.getStatus().getDesc());
                    }
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

        for (OrderEventBO eventBO : eventList) {
            //todo:改为事务消息
            //延时发送订单事件消息，等待事务提交
            rocketMQTemplate.syncSendDelayTimeSeconds(MqTopicConstant.ORDER_EVENT_TOPIC, MessageBuilder.withPayload(eventBO).build(), 2);
        }

        //返回有变化的数量
        return eventList.size();
    }

    @Override
    public Page<OrderListVO> orderList(IPage page, OrderListDTO dto) {
        if(CollUtil.isNotEmpty(dto.getStatusList()) && dto.getStatusList().contains(0)){
            //0查询全部
            dto.setStatusList(null);
        }
        Page<OrderListVO> pageResult = orderMapper.orderList(page, dto, SecurityUtils.getAppUser().getId());

        //我的订单 需计算下一等级佣金
        MemberLevel nextLevel = SecurityUtils.getMemberLevel().nextLevel();
        if(dto.getType() == 1 && nextLevel != null){
            pageResult.getRecords().stream().collect(Collectors.groupingBy(OrderListVO::getPlatformType))
                    .forEach((platformType, orderList) -> {
                        PlatformConfig.CommissionRatio config = platformConfigService.getConfig(platformType, PlatformConfigType.COMMISSION_RATIO);
                        for (OrderListVO vo : orderList) {
                            if(vo.getStatus() != OrderStatus.EXPIRED) {
                                vo.setNextCommission(CommissionUtils.calculate(config, vo.getCommission(), nextLevel).getCommission());
                            }
                        }
                    });
        }
        return pageResult;
    }


}
