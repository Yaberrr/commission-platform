package com.moe.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.constant.HttpStatus;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.enums.user.Gender;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.Assert;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.platform.api.PlatformAuthApi;
import com.moe.platform.vo.AuthUrlVO;
import com.moe.user.mapper.PlatformAuthMapper;
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
    @Autowired
    private PlatformAuthMapper platformAuthMapper;
    @Autowired
    private PlatformAuthApi platformAuthApi;

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
    public AuthUrlVO getPlatformAuthUrl(PlatformType platformType) {
        Assert.isTrue(platformType.isNeedAuth(),"该平台不需要授权");
        User user = SecurityUtils.getAppUser();
        PlatformAuth auth = platformAuthMapper.selectOne(new LambdaQueryWrapper<PlatformAuth>()
                .eq(PlatformAuth::getPlatformType, platformType)
                .eq(PlatformAuth::getUserId, user.getId()));

        if(auth == null){
            //创建授权信息
            auth = platformAuthApi.createAuth(platformType,user.getId()).getData();
            platformAuthMapper.insert(auth);
        }else if (auth.getStatus() == 0){
            //检查授权状态
            if(platformAuthApi.checkAuth(auth).getData()){
                auth.setStatus(1);
                platformAuthMapper.updateById(auth);
            }
        }
        Assert.isTrue(auth.getStatus() == 0,"该平台已授权");

        return platformAuthApi.generateAuthUrl(auth).getData();
    }

}
