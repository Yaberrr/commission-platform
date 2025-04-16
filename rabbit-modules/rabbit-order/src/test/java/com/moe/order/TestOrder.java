package com.moe.order;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.order.OrderEvent;
import com.moe.common.module.domain.bo.OrderEventBO;
import com.moe.common.mq.MqTopicConstant;
import com.moe.order.mapper.OrderMapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tangyabo
 * @date 2025/4/16
 */
@SpringBootTest(classes = MoeOrderApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestOrder {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testSendMessage(){
        Order order = orderMapper.selectById(19L);

        rocketMQTemplate.syncSendDelayTimeSeconds(MqTopicConstant.ORDER_EVENT_TOPIC, MessageBuilder.withPayload(
                new OrderEventBO(order, OrderEvent.NEW_TO_EXPIRED)
        ).build(), 2);

    }
}
