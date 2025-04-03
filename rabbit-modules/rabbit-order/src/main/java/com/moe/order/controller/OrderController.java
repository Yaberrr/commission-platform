package com.moe.order.controller;

import com.moe.common.core.domain.R;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.order.domain.dto.OrderListDTO;
import com.moe.order.domain.vo.OrderListVO;
import com.moe.order.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 订单
 * @author tangyabo
 * @date 2025/4/3
 */
@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Operation(description = "订单列表")
    @PostMapping("/orderList")
    public R<TableDataInfo<OrderListVO>> orderList(@Valid OrderListDTO dto){
        return R.ok(TableDataInfo.build(orderService.orderList(TableSupport.buildPageRequest().buildPage(),dto)));
    }

}
