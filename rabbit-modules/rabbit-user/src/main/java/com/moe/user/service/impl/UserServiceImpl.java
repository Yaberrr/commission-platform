package com.moe.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.user.Gender;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.user.mapper.UserMapper;
import com.moe.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author tangyabo
 * @date 2025/3/12
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User saveUser(User user) {
        User oldUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhoneNumber, user.getPhoneNumber()));
        if(oldUser == null){
            //用户不存在，自动注册
            user.setMemberLevel(MemberLevel.COPPER);
            user.setRegisterTime(new Date());
            user.setLastLoginTime(new Date());
            userMapper.insert(user);
            return user;
        }else{
            //更新信息
            if(StringUtils.isNotBlank(user.getWechat())) {
                oldUser.setWechat(user.getWechat());
            }
            if(StringUtils.isNotBlank(user.getArea())){
                oldUser.setArea(user.getArea());
            }
            if(oldUser.getSex() == Gender.UNKNOWN){
                oldUser.setSex(user.getSex());
            }
            oldUser.setLastLoginTime(new Date());
            userMapper.updateById(oldUser);
            return oldUser;
        }
    }
}
