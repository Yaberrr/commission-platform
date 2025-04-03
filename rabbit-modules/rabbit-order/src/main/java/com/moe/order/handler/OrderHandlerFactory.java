package com.moe.order.handler;

import com.moe.common.core.enums.order.OrderStatus;
import com.moe.common.core.exception.ServiceException;
import com.moe.order.domain.bo.OrderStatusBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangyabo
 * @date 2025/4/3
 */
@Component
public class OrderHandlerFactory {

    private static final Logger log = LoggerFactory.getLogger(OrderHandlerFactory.class);
    private final Map<OrderStatus, OrderHandler> orderHandlerMap;

    @Autowired
    public OrderHandlerFactory(Map<String, OrderHandler> orderHandlerMap) {
        this.orderHandlerMap = new HashMap<>();
        orderHandlerMap.forEach((name, handler) -> {
            try {
                // 匹配枚举名称
                OrderStatus status = OrderStatus.valueOf(name.replace("OrderHandler", "").toUpperCase());
                this.orderHandlerMap.put(status, handler);
            } catch (IllegalArgumentException e) {
                throw new ServiceException("找不到订单状态对应的枚举: " + name, e);
            }
        });
    }

    public boolean changeStatus(OrderStatusBO bo){
        if(bo.getOldStatus() == bo.getNewStatus()){
            return true;
        }
        //当前状态对应的处理器
        OrderHandler handler = orderHandlerMap.get(bo.getOldStatus());
        try {
            handler.handleStatusChange(bo.getOrder(),bo.getNewStatus());
            return true;
        }catch (ServiceException e){
            log.error("订单状态修改失败-{}",e.getMessage());
            return false;
        }
    }


}
