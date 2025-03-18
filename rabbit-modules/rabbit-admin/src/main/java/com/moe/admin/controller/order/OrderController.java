package com.moe.admin.controller.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.service.ISysOrderService;
import com.moe.admin.domain.dto.order.OrderListDTO;
import com.moe.admin.domain.vo.order.OrderVO;
import com.moe.common.core.utils.poi.ExcelUtil;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
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
        Page<OrderVO> list = orderService.selectOrder(TableSupport.buildPageRequest().buildPage(),orderListDTO);
        return getDataTable(list);
    }

    @RequiresPermissions("system:order:export")
    @GetMapping("/export")
    public void export(HttpServletResponse response, OrderListDTO orderListDTO)
    {
        Page page = new Page<>(1, 20000);
        Page<OrderVO> list = orderService.selectOrder(page, orderListDTO);
        ExcelUtil<OrderVO> util = new ExcelUtil<OrderVO>(OrderVO.class);
        util.exportExcel(response, list.getRecords(), "订单表");
    }
}
