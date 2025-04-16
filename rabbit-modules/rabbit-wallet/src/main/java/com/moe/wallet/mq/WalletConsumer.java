package com.moe.wallet.mq;

import com.moe.common.core.domain.order.Order;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.module.domain.bo.OrderEventBO;
import com.moe.common.mq.MqGroupConstant;
import com.moe.common.mq.MqTopicConstant;
import com.moe.wallet.service.ICommissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 消费者
 * @author tangyabo
 * @date 2025/4/16
 */
@Slf4j
@Component
public class WalletConsumer {

    @Autowired
    private ICommissionService commissionService;

    @Service
    @RocketMQMessageListener(topic = MqTopicConstant.ORDER_EVENT_TOPIC, consumerGroup = MqGroupConstant.WALLET_GROUP)
    public class OrderEventConsumer implements RocketMQListener<OrderEventBO> {
        public void onMessage(OrderEventBO bo) {
            Order order = bo.getOrder();
            log.info("received order event message: {},{}", order.getId(), bo.getEvent());
            switch (bo.getEvent()){
                case NEW_TO_PAID:
                case NEW_TO_RECEIVED:
                    //发放佣金
                    commissionService.addCommission(order);
                    break;
                case NEW_TO_ACCOUNTED:
                    //发放佣金直接到账
                    commissionService.addCommission(order);
                    commissionService.commissionToAccount(order);
                    break;
                case PAID_TO_ACCOUNTED:
                case RECEIVED_TO_ACCOUNTED:
                    //佣金到帐
                    commissionService.commissionToAccount(order);
                    break;
                case PAID_TO_EXPIRED:
                case RECEIVED_TO_EXPIRED:
                    //佣金失效
                    commissionService.deductCommission(order);
                    break;
                default:
                    //其余事件不处理
            }
        }
    }

}
