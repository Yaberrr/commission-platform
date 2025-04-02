package com.moe.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.user.Gender;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.redis.service.RedisService;
import com.moe.user.mapper.UserMapper;
import com.moe.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author tangyabo
 * @date 2025/3/12
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

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

    @Override
    public Map<String, Integer> userMemberLevelMap() {
        Map<String, Integer> map = redisService.getCacheMap(CacheConstants.USER_MEMBER_LEVEL_KEY);
        if(CollUtil.isEmpty(map)){
            List<User> userList = userMapper.selectList(new LambdaQueryWrapper<User>().select(User::getId, User::getMemberLevel));
            map = userList.stream().collect(Collectors.toMap(u -> u.getId().toString(), u -> u.getMemberLevel().getCode()));
            redisService.setCacheMap(CacheConstants.USER_MEMBER_LEVEL_KEY,map);
            //缓存一小时
            redisService.expire(CacheConstants.USER_MEMBER_LEVEL_KEY,1,TimeUnit.HOURS);
        }
        return map;
    }

}
