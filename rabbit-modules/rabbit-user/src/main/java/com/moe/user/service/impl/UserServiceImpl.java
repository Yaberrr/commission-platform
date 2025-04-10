package com.moe.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.Assert;
import com.moe.common.core.utils.bean.BeanCopyUtils;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.user.mapper.UserMapper;
import com.moe.user.service.IUserService;
import com.moe.user.utils.YbCodeGenerator;
import com.moe.user.vo.UserMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private YbCodeGenerator ybCodeGenerator;

    @Override
    public User saveUser(User user) {
        User oldUser;
        if(SecurityUtils.getAppUser() != null){
            //已登录
            oldUser = SecurityUtils.getAppUser();
        } else if(StrUtil.isNotBlank(user.getPhoneNumber())){
            //手机登录
            oldUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhoneNumber, user.getPhoneNumber()));
        }else if(StrUtil.isNotBlank(user.getWechatOpenId())){
            //微信登录
            oldUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getWechatOpenId, user.getWechatOpenId()));
        }else{
            throw new ServiceException("未包含用户标识");
        }
        if(oldUser == null){
            //用户不存在，自动注册
            user.setMemberLevel(MemberLevel.COPPER);
            user.setRegisterTime(new Date());
            user.setLastLoginTime(new Date());
            //生成元宝号
            user.setYbCode(ybCodeGenerator.get());
            userMapper.insert(user);
            return user;
        }else{
            if(StrUtil.isBlank(oldUser.getPhoneNumber()) && StrUtil.isNotBlank(user.getPhoneNumber())){
                Assert.isTrue(userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getPhoneNumber,user.getPhoneNumber())) > 0,
                        "该手机号已绑定其他账号");
                oldUser.setPhoneNumber(user.getPhoneNumber());
            }
            if(StrUtil.isBlank(oldUser.getWechatOpenId()) && StrUtil.isNotBlank(user.getWechatOpenId())){
                Assert.isTrue(userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getWechatOpenId,user.getWechatOpenId())) > 0,
                        "该微信已绑定其他账号");
                oldUser.setWechatOpenId(user.getWechatOpenId());
            }
            //更新最后登录时间
            oldUser.setLastLoginTime(new Date());
            userMapper.updateById(oldUser);
            return oldUser;
        }
    }


    @Override
    public Map<String, UserMemberVO> getUserMemberMap() {
        List<User> userList = userMapper.selectList(new LambdaQueryWrapper<User>().select(User::getId, User::getMemberLevel,
                User::getParentId,User::getGrandparentId));
        return userList.stream().collect(Collectors.toMap(u -> u.getId().toString(), user -> BeanCopyUtils.copy(user,UserMemberVO.class)));
    }

}
