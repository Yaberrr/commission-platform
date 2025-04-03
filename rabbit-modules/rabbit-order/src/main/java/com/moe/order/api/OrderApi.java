package com.moe.order.api;

import com.moe.common.core.domain.R;
import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.order.dto.BatchUpdateOrderDTO;
import com.moe.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author tangyabo
 * @date 2025/4/2
 */
@RestController
@RequestMapping("/orderInner")
public class OrderApi implements IOrderApi {

    @Autowired
    private IOrderService orderService;

    @InnerAuth
    @Override
    public R<Integer> batchUpdateOrder(@Valid BatchUpdateOrderDTO dto) {
        return R.ok(orderService.batchUpdateOrder(dto));
    }


}
