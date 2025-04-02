package com.moe.platform.api;

import com.moe.common.core.domain.R;
import com.moe.common.security.annotation.InnerAuth;
import com.moe.platform.dto.order.PlatformOrderDTO;
import com.moe.platform.service.IPlatformOrderService;
import com.moe.platform.service.PlatformServiceFactory;
import com.moe.platform.vo.PlatformOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 平台订单
 * @author tangyabo
 * @date 2025/3/28
 */
@RestController
@RequestMapping("/platformOrder")
public class PlatformOrderApi implements IPlatformOrderApi{

    @Autowired
    private PlatformServiceFactory platformServiceFactory;

    @InnerAuth
    @Override
    public R<Integer> totalCount(PlatformOrderDTO dto) {
        IPlatformOrderService orderService = platformServiceFactory.getOrderService(dto.getPlatformType());
        return R.ok(orderService.totalCount(dto));
    }

    @InnerAuth
    @Override
    public R<List<PlatformOrderVO>> orderList(PlatformOrderDTO dto) {
        IPlatformOrderService orderService = platformServiceFactory.getOrderService(dto.getPlatformType());
        return R.ok(orderService.orderList(dto));
    }
}
