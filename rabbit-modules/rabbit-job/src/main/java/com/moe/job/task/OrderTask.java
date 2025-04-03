package com.moe.job.task;

import cn.hutool.core.collection.CollUtil;
import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.domain.config.PlatformConfig;
import com.moe.common.core.domain.order.Order;
import com.moe.common.core.enums.config.PlatformConfigType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.utils.bean.BeanCopyUtils;
import com.moe.common.module.domain.vo.CommissionCalculateVO;
import com.moe.common.module.service.PlatformConfigService;
import com.moe.common.module.utils.CommissionUtils;
import com.moe.common.redis.service.RedisService;
import com.moe.order.api.IOrderApi;
import com.moe.order.dto.BatchUpdateOrderDTO;
import com.moe.platform.api.IPlatformAuthApi;
import com.moe.platform.api.IPlatformOrderApi;
import com.moe.platform.dto.order.PlatformOrderDTO;
import com.moe.platform.vo.PlatformOrderVO;
import com.moe.user.api.IUserApi;
import com.moe.user.vo.UserMemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
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
    @Autowired
    private RedisService redisService;

    public void updateOrder(Integer platformType, long seconds) {
        log.info("开始同步订单...");
        long startTime = System.currentTimeMillis()/1000 - seconds;
        long endTime = System.currentTimeMillis()/1000;
        //查询总数
        PlatformOrderDTO countDto = new PlatformOrderDTO();
        countDto.setStartTime(startTime);
        countDto.setEndTime(endTime);
        countDto.setPlatformType(platformType);
        int orderCount = platformOrderApi.totalCount(countDto).getData();
        if(orderCount <= 0){
            log.info("订单数为0，停止同步...");
            return;
        }
        log.info("查询到订单数{}条",orderCount);
        OrderTask thisBean = applicationContext.getBean(OrderTask.class);
        //加载用户授权信息
        Map<String, Long> authUserMap = thisBean.getAuthUserMap(platformType);
        //加载用户会员等级
        Map<String, UserMemberVO> userMemberMap = thisBean.getUserMemberMap();
        //加载佣金配置
        PlatformConfig.CommissionRatio commissionConfig = platformConfigService.getConfig(PlatformType.PDD, PlatformConfigType.COMMISSION_RATIO);

        //分批处理 每批处理50条
        // 计算总页数
        int totalPages = (int) Math.ceil((double)orderCount/BATCH_SIZE);
        // 提交任务
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            int pageNum = i + 1;
            futures.add(CompletableFuture.supplyAsync(() -> {
                    //查询订单
                    PlatformOrderDTO dto = new PlatformOrderDTO();
                    dto.setStartTime(startTime);
                    dto.setEndTime(endTime);
                    dto.setPageNum(pageNum);
                    dto.setPageSize(BATCH_SIZE);
                    dto.setPlatformType(platformType);
                    List<PlatformOrderVO> orderVOList = platformOrderApi.orderList(dto).getData();

                    //处理平台订单数据
                    List<Order> orderList = orderVOList.stream().map(vo -> {
                        Order order = new Order();
                        BeanCopyUtils.copy(vo, order);
                        order.setPlatformType(dto.getPlatformType());
                        order.setUserId(authUserMap.get(vo.getAuthId()));

                        UserMemberVO userVO = userMemberMap.get(order.getUserId().toString());
                        MemberLevel memberLevel = userVO.getMemberLevel();
                        //计算佣金
                        CommissionCalculateVO calculateVO = CommissionUtils.calculate(commissionConfig, vo.getPlatformCommission(), memberLevel);
                        order.setAllocatedCommission(calculateVO.getAllocatedCommission());
                        order.setCommission(calculateVO.getCommission());
                        if(userVO.getParentId() != null){
                            order.setParentUserId(userVO.getParentId());
                            order.setParentCommission(calculateVO.getParentCommission());
                        }
                        if(userVO.getGrandparentId() != null){
                            order.setGrandParentUserId(userVO.getGrandparentId());
                            order.setGrandParentCommission(calculateVO.getGrandParentCommission());
                        }
                        return order;
                    }).collect(Collectors.toList());
                    //更新订单
                    BatchUpdateOrderDTO updateOrderDTO = new BatchUpdateOrderDTO();
                    updateOrderDTO.setOrderList(orderList);
                    updateOrderDTO.setPlatformType(dto.getPlatformType());
                    return orderApi.batchUpdateOrder(updateOrderDTO).getData();
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
        log.info("同步订单完成，同步订单{}条", successCount);
    }


    //用户认证信息 允许短暂缓存不同步
    public Map<String, Long> getAuthUserMap(Integer platformType){
        Map<String, Long> map = redisService.getCacheMap(CacheConstants.PLATFORM_AUTH_MAP_KEY);
        if(CollUtil.isEmpty(map)){
            map = platformAuthApi.authUserMap(PlatformType.fromCode(platformType)).getData();
            redisService.setCacheMap(CacheConstants.PLATFORM_AUTH_MAP_KEY, map);
            //缓存十分钟
            redisService.expire(CacheConstants.PLATFORM_AUTH_MAP_KEY, 10, TimeUnit.MINUTES);
        }
        return map;
    }

    //用户会员等级 需保证缓存和数据库一致
    public Map<String, UserMemberVO> getUserMemberMap(){
        Map<String, UserMemberVO> map = redisService.getCacheMap(CacheConstants.USER_MEMBER_MAP_KEY);
        if(CollUtil.isEmpty(map)){
            map = userApi.userMemberMap().getData();
            redisService.setCacheMap(CacheConstants.USER_MEMBER_MAP_KEY,map);
            //缓存一天
            redisService.expire(CacheConstants.USER_MEMBER_MAP_KEY,1, TimeUnit.DAYS);
        }
        return map;
    }
}
