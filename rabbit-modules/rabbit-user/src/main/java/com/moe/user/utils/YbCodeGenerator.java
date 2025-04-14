package com.moe.user.utils;

import com.moe.common.core.constant.CacheConstants;
import com.moe.user.config.BloomFilterConfig;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 元宝号生成器
 * @author tangyabo
 * @date 2025/4/9
 */
@Component
public class YbCodeGenerator {

    @Autowired
    private RedissonClient redissonClient;

    private static final int MIN = 100000; // 最少6位

    private static final int MAX = 999999999; // 最多9位

    private final Random random = new Random();

    public String get() {
        RBloomFilter<Integer> ybCodeFilter = redissonClient.getBloomFilter(BloomFilterConfig.YB_CODE_FILTER);
        int code;
        do {
            code = MIN + random.nextInt(MAX - MIN + 1);
        }//检查布隆过滤器
        while (ybCodeFilter.contains(code));

        //刷新布隆过滤器
        ybCodeFilter.add(code);
        return Integer.toString(code);
    }

}
