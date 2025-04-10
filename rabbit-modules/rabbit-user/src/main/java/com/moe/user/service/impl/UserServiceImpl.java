package com.moe.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.domain.config.UserConfig;
import com.moe.common.core.domain.user.User;
import com.moe.common.core.enums.config.UserConfigType;
import com.moe.common.core.enums.user.MemberLevel;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.Assert;
import com.moe.common.core.utils.bean.BeanCopyUtils;
import com.moe.common.core.vo.PairVO;
import com.moe.common.security.service.TokenService;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.user.domain.dto.UserConfigDTO;
import com.moe.user.domain.dto.UserDTO;
import com.moe.user.mapper.UserConfigMapper;
import com.moe.user.mapper.UserMapper;
import com.moe.user.service.IUserService;
import com.moe.user.utils.YbCodeGenerator;
import com.moe.user.vo.UserMemberVO;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private UserConfigMapper userConfigMapper;
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

        User returnUser;
        if(oldUser == null){
            //用户不存在，自动注册
            user.setMemberLevel(MemberLevel.COPPER);
            user.setRegisterTime(new Date());
            user.setLastLoginTime(new Date());
            //生成元宝号
            user.setYbCode(ybCodeGenerator.get());
            userMapper.insert(user);
            returnUser = user;
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
            returnUser = oldUser;
        }
        //加载配置
        returnUser.setConfigList(this.loadUserConfig(user.getId()));
        return returnUser;
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

    /**
     * 加载用户配置
     * @param userId
     * @return
     */
    public List<PairVO<Integer,String>> loadUserConfig(Long userId){
        Map<UserConfigType, String> configMap = userConfigMapper.selectList(new LambdaQueryWrapper<UserConfig>().eq(UserConfig::getUserId, userId))
                .stream().collect(Collectors.toMap(UserConfig::getConfigType, UserConfig::getConfigValue));
        //补充默认配置
        for (UserConfigType configType : UserConfigType.values()) {
            configMap.computeIfAbsent(configType, UserConfigType::getDefaultValue);
        }
        List<PairVO<Integer,String>> pairList = new ArrayList<>();
        configMap.forEach((k,v) -> pairList.add(new PairVO<>(k.getCode(),v)));
        return pairList;
    }


    @Override
    public Map<String, UserMemberVO> getUserMemberMap() {
        List<User> userList = userMapper.selectList(new LambdaQueryWrapper<User>().select(User::getId, User::getMemberLevel,
                User::getParentId,User::getGrandparentId));
        return userList.stream().collect(Collectors.toMap(u -> u.getId().toString(), user -> BeanUtil.copyProperties(user,UserMemberVO.class)));
    }

    @Override
    public void updateInfo(UserDTO dto) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = userMapper.selectById(loginUser.getAppUser().getId());
        BeanUtil.copyProperties(dto,user,new CopyOptions().ignoreNullValue());
        userMapper.updateById(user);
        //刷新缓存
        loginUser.setAppUser(user);
        tokenService.refreshUserInfo(loginUser);
    }

    @Override
    public void updateConfig(UserConfigDTO dto) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getAppUser().getId();

        UserConfig oldConfig = userConfigMapper.selectOne(new LambdaQueryWrapper<UserConfig>()
                .eq(UserConfig::getConfigType, dto.getConfigType())
                .eq(UserConfig::getUserId, userId));
        if(oldConfig == null){
            //新增配置
            UserConfig newConfig = new UserConfig();
            newConfig.setConfigType(dto.getConfigType());
            newConfig.setConfigValue(dto.getConfigValue());
            newConfig.setUserId(userId);
            userConfigMapper.insert(newConfig);
        }else{
            //修改配置
            oldConfig.setConfigValue(dto.getConfigValue());
            userConfigMapper.updateById(oldConfig);
        }
        //刷新缓存
        loginUser.getAppUser().setConfigList(this.loadUserConfig(userId));
        tokenService.refreshUserInfo(loginUser);
    }


}
