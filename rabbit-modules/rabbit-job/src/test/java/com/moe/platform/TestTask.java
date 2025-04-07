package com.moe.platform;

import com.moe.common.core.enums.platform.PlatformType;
import com.moe.job.MoeJobApplication;
import com.moe.job.task.OrderTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * 京东api测试
 * @author tangyabo
 * @date 2025/3/6
 */
@SpringBootTest(classes = MoeJobApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTask {

    @Autowired
    private OrderTask orderTask;

    @Test
    public void testOrderTask() {
        orderTask.updateOrder(PlatformType.PDD.getCode(), 7200L);
    }

}
