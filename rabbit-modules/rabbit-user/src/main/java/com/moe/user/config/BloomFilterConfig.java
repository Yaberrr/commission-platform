package com.moe.user.config;

import com.moe.user.mapper.UserMapper;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/9
 */

@Configuration
public class BloomFilterConfig {

    //元宝码过滤器
    public static final String YB_CODE_FILTER = "ybCodeFilter";
    //预期数量
    private static final int EXPECTED_INSERTIONS = 1000000;
    //误判率
    private static final double FALSE_POSITIVE_RATE = 0.01;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    public void initBloomFilter() {
        //初始化布隆过滤器
        RBloomFilter<Integer> ybCodeFilter = redissonClient.getBloomFilter(YB_CODE_FILTER);
        if (ybCodeFilter.isExists()) {
            ybCodeFilter.delete();
        }
        ybCodeFilter.tryInit(EXPECTED_INSERTIONS, FALSE_POSITIVE_RATE);
        //加载所有已存在元宝码
        List<Integer> codeList = userMapper.selectAllYbCode();
        codeList.forEach(ybCodeFilter::add);
    }


}
