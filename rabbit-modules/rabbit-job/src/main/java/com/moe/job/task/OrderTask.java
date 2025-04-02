package com.moe.job.task;

import com.moe.platform.api.IPlatformOrderApi;
import com.moe.platform.dto.order.PlatformOrderDTO;
import com.moe.platform.vo.PlatformOrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
@Component("orderTask")
public class OrderTask {

    private static final Logger log = LoggerFactory.getLogger(OrderTask.class);

    private static final int BATCH_SIZE = 50;

    @Autowired
    private IPlatformOrderApi platformOrderApi;
    @Resource
    private ThreadPoolTaskExecutor executorService;

    public void updateOrder(Integer platformType, long seconds) {
        log.info("开始同步订单...");
        long startTime = System.currentTimeMillis()/1000;
        long endTime = System.currentTimeMillis()/1000 - seconds;
        //查询总数
        PlatformOrderDTO countDto = new PlatformOrderDTO();
        countDto.setStartTime(startTime);
        countDto.setEndTime(endTime);
        countDto.setPlatformType(platformType);
        int count = platformOrderApi.totalCount(countDto).getData();
        if(count <= 0){
            log.info("订单数为0，停止同步...");
            return;
        }

        //分批处理 每批处理50条
        // 计算总页数
        int totalPages = (int) Math.ceil((double)count/BATCH_SIZE);
        // 提交任务
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            int pageNum = i + 1;
            futures.add(CompletableFuture.supplyAsync(() -> {
                    PlatformOrderDTO dto = new PlatformOrderDTO();
                    dto.setStartTime(startTime);
                    dto.setEndTime(endTime);
                    dto.setPageNum(pageNum);
                    dto.setPageSize(BATCH_SIZE);
                    dto.setPlatformType(platformType);
                    return this.batchUpdateOrder(dto);
                }, executorService).handle((result, exception) -> {
                if (exception != null) {
                    log.error("同步订单失败, 第{}页发生异常", pageNum, exception);
                    return 0;
                }
                return result;
            }));
        }

        // 汇总所有成功记录数
        int successCount = futures.stream().mapToInt(CompletableFuture::join).sum();
        log.info("同步订单完成，共同步{}条订单", successCount);
    }

    /**
     * 批量保存订单
     * @param dto
     * @return
     */
    private int batchUpdateOrder(PlatformOrderDTO dto){
        List<PlatformOrderVO> orderList = platformOrderApi.orderList(dto).getData();


        return 0;

    }
}
