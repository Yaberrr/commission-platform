package com.moe.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.Assert;
import com.moe.common.core.utils.bean.BeanCopyUtils;
import com.moe.common.security.service.TokenService;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.user.mapper.UserMapper;
import com.moe.user.service.IUserService;
import com.moe.user.utils.YbCodeGenerator;
import com.moe.user.vo.UserMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private TokenService tokenService;

    @Override
    @Transactional
    public User saveUser(User user) {
        User oldUser;
        if(SecurityUtils.getAppUser() != null){
            //已登录
            oldUser = userMapper.selectById(SecurityUtils.getAppUser().getId());
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
                //绑定手机号
                oldUser = this.bindPhoneNumber(oldUser,user.getPhoneNumber());
            }else if(StrUtil.isBlank(oldUser.getWechatOpenId()) && StrUtil.isNotBlank(user.getWechatOpenId())){
                //绑定微信
                oldUser = this.bindWechatOpenId(oldUser,user.getWechatOpenId());
            }
            //更新最后登录时间
            oldUser.setLastLoginTime(new Date());
            userMapper.updateById(oldUser);
            return oldUser;
        }
    }

    /**
     * 绑定手机号
     * @param wechatUser 微信用户
     * @param phoneNumber 手机号
     */
    public User bindPhoneNumber(User wechatUser, String phoneNumber){
        Assert.isFalse(StrUtil.isNotBlank(wechatUser.getPhoneNumber()), "当前用户已绑定手机号");
        User phoneUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhoneNumber, phoneNumber));
        if(phoneUser == null){
            //对应手机用户不存在 直接绑定
            wechatUser.setPhoneNumber(phoneNumber);
            userMapper.updateById(wechatUser);
            return wechatUser;
        }
        //手机用户已绑微信。不可合并
        Assert.isFalse(StrUtil.isNotBlank(phoneUser.getWechatOpenId()),"手机号已绑定其他用户");
        return this.combineUser(phoneUser,wechatUser);
    }

    /**
     * 绑定微信
     * @param phoneUser  手机用户
     * @param wechatOpenId 微信号
     */
    public User bindWechatOpenId(User phoneUser, String wechatOpenId){
        Assert.isFalse(StrUtil.isNotBlank(phoneUser.getWechatOpenId()), "当前用户已绑定微信号");
        User wechatUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getWechatOpenId, wechatOpenId));
        if(wechatUser == null){
            //对应微信用户不存在 直接绑定
            phoneUser.setWechatOpenId(phoneUser.getWechatOpenId());
            userMapper.updateById(phoneUser);
            return phoneUser;
        }
        //微信用户已绑手机号。不可合并
        Assert.isFalse(StrUtil.isNotBlank(wechatUser.getPhoneNumber()),"微信号已绑定其他用户");
        return this.combineUser(phoneUser,wechatUser);
    }

    /**
     * 合并用户
     * @param phoneUser 手机用户
     * @param wechatUser 微信用户
     */
    public User combineUser(User phoneUser, User wechatUser){
        //合并微信用户到手机用户上
        phoneUser.setWechatOpenId(wechatUser.getWechatOpenId());
        if(StrUtil.isBlank(phoneUser.getAvatarUrl())) {
            phoneUser.setAvatarUrl(wechatUser.getAvatarUrl());
        }
        tokenService.delUserInfo(wechatUser);
        userMapper.deleteById(wechatUser);
        userMapper.updateById(phoneUser);
        return phoneUser;
    }



    @Override
    public Map<String, UserMemberVO> getUserMemberMap() {
        List<User> userList = userMapper.selectList(new LambdaQueryWrapper<User>().select(User::getId, User::getMemberLevel,
                User::getParentId,User::getGrandparentId));
        return userList.stream().collect(Collectors.toMap(u -> u.getId().toString(), user -> BeanCopyUtils.copy(user,UserMemberVO.class)));
    }

}
