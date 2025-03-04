package com.moe.product;

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
public class MoeMallConnectorApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(MoeMallConnectorApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  电商对接模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "         (\\(\\     \n" +
                "         ( -.-)    \n" +
                "        o_(\")(\")  ");
    }
}
