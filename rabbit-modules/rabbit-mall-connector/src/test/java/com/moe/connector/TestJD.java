package com.moe.connector;

import com.moe.product.MoeMallConnectorApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 京东api测试
 * @author tangyabo
 * @date 2025/3/6
 */
@SpringBootTest(classes = MoeMallConnectorApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestJD {

    @Test
    public void test(){
//        JdClient client=new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret);
    }

}
