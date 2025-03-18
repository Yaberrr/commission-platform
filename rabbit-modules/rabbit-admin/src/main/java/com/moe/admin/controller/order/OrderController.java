package com.moe.admin.controller.order;

import com.moe.admin.service.ISysOrderService;
import com.moe.common.core.domain.dto.order.OrderListDTO;
import com.moe.common.core.domain.vo.order.OrderVO;
import com.moe.common.core.utils.poi.ExcelUtil;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private ISysOrderService orderService;

    @RequiresPermissions("system:order:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderListDTO orderListDTO)
    {
        startPage();
        List<OrderVO> list = orderService.selectOrder(orderListDTO);
        return getDataTable(list);
    }

    @RequiresPermissions("system:order:export")
    @GetMapping("/export")
    public void export(HttpServletResponse response, OrderListDTO orderListDTO)
    {
        List<OrderVO> list = orderService.selectOrder(orderListDTO);
        ExcelUtil<OrderVO> util = new ExcelUtil<OrderVO>(OrderVO.class);
        util.exportExcel(response, list, "订单表");
    }
}
