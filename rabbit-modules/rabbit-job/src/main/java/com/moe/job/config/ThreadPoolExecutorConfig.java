package com.moe.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 * @author tangyabo
 * @date 2024/4/16
 */
@Configuration
public class ThreadPoolExecutorConfig {
    // 核心线程数
    private static final int CORE_POOL_SIZE = 8;

    // 最大线程数
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2 + 1;

    @Bean(name="executorService")
    public ThreadPoolTaskExecutor executorService(){
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        poolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        //设置最大线程数
        poolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        //除核心线程外的线程存活时间
        poolTaskExecutor.setKeepAliveSeconds(60);
        //阻塞队列大小，传入值大于0，使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        poolTaskExecutor.setQueueCapacity(50);
        //线程名称前缀
        poolTaskExecutor.setThreadNamePrefix("thread-pool-");
        //设置拒绝策略
        poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return poolTaskExecutor;
    }

}
