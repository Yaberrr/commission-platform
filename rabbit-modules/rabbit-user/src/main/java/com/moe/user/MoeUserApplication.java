package com.moe.user;

import com.moe.common.security.annotation.EnableCustomConfig;
import com.moe.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tangyabo
 * @date 2025/3/3
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class MoeUserApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(MoeUserApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  用户模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "         (\\(\\     \n" +
                "         ( -.-)    \n" +
                "        o_(\")(\")  ");
    }
}
