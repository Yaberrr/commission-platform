package com.moe.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.order.domain.dto.OrderListDTO;
import com.moe.order.domain.vo.OrderListVO;
import com.moe.order.dto.BatchUpdateOrderDTO;

/**
 * @author tangyabo
 * @date 2025/4/2
 */
public interface IOrderService {

    /**
     * 批量更新订单
     * @param dto
     * @return
     */
    int batchUpdateOrder(BatchUpdateOrderDTO dto);

    /**
     * 查询订单列表
     * @param page
     * @param dto
     * @return
     */
    Page<OrderListVO> orderList(IPage page, OrderListDTO dto);
}
