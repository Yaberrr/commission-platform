package com.moe.auth;

import com.moe.auth.service.AppLoginService;
import com.moe.common.core.domain.user.User;
import com.moe.user.api.UserApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Pattern;

/**
 * @author tangyabo
 * @date 2025/3/12
 */
@SpringBootTest(classes = MoeAuthApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAuth {

    @Autowired
    private AppLoginService appLoginService;
    @Autowired
    private UserApi userApi;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d*$");


    @Test
    public void testSendCode(){
       /* User user = new User();
        user.setPhoneNumber("13316022418");
        userApi.saveUser(user);*/
        appLoginService.sendCode("19073170919");
    }

    public static void main(String[] args) {
        System.out.println(PHONE_PATTERN.matcher("23316022418").matches());
    }
}
