package com.moe.platform.service;

import com.moe.platform.dto.order.PlatformOrderDTO;
import com.moe.platform.vo.PlatformOrderVO;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/3/31
 */
public interface IPlatformOrderService {

    /**
     * 查询订单总数
     * @param dto 查询条件
     * @return
     */
    int totalCount(PlatformOrderDTO dto);

    /**
     * 分页查询订单
     * @param dto 查询条件
     * @return
     */
    List<PlatformOrderVO> orderList(PlatformOrderDTO dto);

}
