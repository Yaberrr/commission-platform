package com.moe.job.task;

import com.moe.common.core.domain.config.PlatformConfig;
import com.moe.common.core.domain.order.Order;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.config.PlatformConfigType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.utils.bean.BeanCopyUtils;
import com.moe.common.module.domain.vo.CommissionCalculateVO;
import com.moe.common.module.service.PlatformConfigService;
import com.moe.common.module.utils.CommissionUtils;
import com.moe.order.api.IOrderApi;
import com.moe.platform.api.IPlatformAuthApi;
import com.moe.platform.api.IPlatformOrderApi;
import com.moe.platform.dto.order.PlatformOrderDTO;
import com.moe.platform.vo.PlatformOrderVO;
import com.moe.user.api.IUserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
    @Autowired
    private IPlatformAuthApi platformAuthApi;
    @Autowired
    private IUserApi userApi;
    @Resource
    private ThreadPoolTaskExecutor executorService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PlatformConfigService platformConfigService;
    @Autowired
    private IOrderApi orderApi;

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
        OrderTask thisBean = applicationContext.getBean(OrderTask.class);
        //加载授权信息
        Map<String, Long> authUserMap = thisBean.getAuthUserMap(PlatformType.fromCode(platformType));
        //加载佣金配置
        PlatformConfig.CommissionRatio commissionConfig = platformConfigService.getConfig(PlatformType.PDD, PlatformConfigType.COMMISSION_RATIO);
        //加载用户会员等级
        Map<String, Integer> userMemberLevelMap = userApi.userMemberLevelMap().getData();

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
                    return this.batchUpdateOrder(dto,authUserMap,commissionConfig,userMemberLevelMap);
                }, executorService).handle((result, exception) -> {
                if (exception != null) {
                    log.error("同步订单失败, 第{}页发生异常", pageNum, exception);
                    return 0;
                }
                return result;
            }));
        }

        //汇总
        int successCount = futures.stream().mapToInt(CompletableFuture::join).sum();
        log.info("同步订单完成，共同步{}条订单", successCount);
    }

    /**
     * 批量更新订单
     * @param dto
     * @param authUserMap 授权用户
     * @return
     */
    private int batchUpdateOrder(PlatformOrderDTO dto,Map<String,Long> authUserMap,
                                 PlatformConfig.CommissionRatio commissionConfig, Map<String,Integer> userMemberLevelMap){
        //查询订单
        List<PlatformOrderVO> orderVOList = platformOrderApi.orderList(dto).getData();

        //处理平台订单数据
        List<Order> orderList = orderVOList.stream().map(vo -> {
            Order order = new Order();
            BeanCopyUtils.copy(vo, order);
            order.setPlatformType(dto.getPlatformType());
            order.setUserId(authUserMap.get(vo.getAuthId()));
            MemberLevel memberLevel = MemberLevel.fromCode(userMemberLevelMap.get(order.getUserId().toString()));
            //计算佣金
            CommissionCalculateVO calculateVO = CommissionUtils.calculate(commissionConfig, vo.getPlatformCommission(), memberLevel);
            BeanCopyUtils.copy(calculateVO, order);
            return order;
        }).collect(Collectors.toList());
        //更新订单
        return orderApi.updateOrder(orderList).getData();
    }

    //五分钟刷新一次
    @Cacheable(value = "authUserMap", key="#platformType")
    public Map<String, Long> getAuthUserMap(PlatformType platformType){
        return platformAuthApi.authUserMap(platformType).getData();
    }
}
