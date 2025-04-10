package com.moe.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.domain.user.User;
import com.moe.user.mapper.UserMapper;
import com.moe.user.service.IUserService;
import com.moe.user.utils.YbCodeGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/9
 */
@SpringBootTest(classes = MoeUserApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUser {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private YbCodeGenerator generator;

    @Test
    public void testGenerateCode(){
        List<User> userList = userMapper.selectList(new LambdaQueryWrapper<>());
        for (User user : userList) {
            user.setYbCode(generator.get());
            userMapper.updateById(user);
        }
    }
}
