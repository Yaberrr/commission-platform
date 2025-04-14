package com.moe.admin.queue;

import com.moe.admin.mapper.IndexDiyMapper;
import com.moe.common.core.domain.IndexDiy;
import com.moe.common.core.enums.PublishStatus;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 首页装修 延时队列
 * @author tangyabo
 * @date 2025/4/14
 */
@Component
public class IndexDiyDelayQueue {

    private static final String INDEX_DIY_DELAY_QUEUE_KEY = "indexDiy:delayQueue";
    private static final Logger log = LoggerFactory.getLogger(IndexDiyDelayQueue.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IndexDiyMapper indexDiyMapper;

    /**
     * 提交任务
     * @param indexDiyId 装修id
     * @param delay 延迟时间
     * @param timeUnit 时间单位
     */
    public void addTask(String indexDiyId, long delay, TimeUnit timeUnit) {
        // 获取阻塞队列
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(INDEX_DIY_DELAY_QUEUE_KEY);
        // 获取延迟队列
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        log.info("添加装修{}到定时发布队列，延迟{}", indexDiyId, delay + " " + timeUnit);
        delayedQueue.offer(indexDiyId, delay, timeUnit);
    }

    /**
     * 完成任务
     */
    @PostConstruct
    public void completeTask(){
        new Thread(() -> {
            while (true) {
                try {
                    RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(INDEX_DIY_DELAY_QUEUE_KEY);
                    // 阻塞等待直到队列中有元素
                    String indexDiyId = blockingDeque.take();
                    IndexDiy indexDiy = indexDiyMapper.selectById(Long.valueOf(indexDiyId));
                    if(indexDiy!=null && new Date().compareTo(indexDiy.getSchedulePublishTime()) >= 0 &&
                            indexDiy.getPublishStatus() == PublishStatus.SCHEDULED){
                        indexDiy.setPublishStatus(PublishStatus.PUBLISHED);
                        indexDiy.setLastPublishTime(new Date());
                        indexDiyMapper.updateById(indexDiy);
                        log.info("定时发布装修{}成功",indexDiy.getId());
                    }else{
                        log.info("装修信息发生变化，取消定时发布");
                    }
                }catch (Exception e) {
                    log.error("处理装修定时发布异常", e);
                }
            }
        }).start();
    }

}
