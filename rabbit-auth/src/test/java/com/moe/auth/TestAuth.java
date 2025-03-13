package com.moe.auth;

import com.moe.auth.service.AppLoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tangyabo
 * @date 2025/3/12
 */
@SpringBootTest(classes = MoeAuthApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAuth {

    @Autowired
    private AppLoginService appLoginService;

    @Test
    public void testSendCode(){
        appLoginService.sendCode("19073170919");
    }
}
