package com.moe.common.module.handler;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import com.moe.common.core.constant.HttpStatus;
import com.moe.common.core.domain.LoginUser;
import com.moe.common.core.enums.SystemType;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.web.domain.AddBaseEntity;
import com.moe.common.core.web.domain.BaseEntity;
import com.moe.common.security.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Objects;

/**
 * MP注入处理器
 *
 * @author Lion Li
 * @date 2021/4/25
 */
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            if (ObjectUtil.isNotNull(metaObject)){
                if(metaObject.getOriginalObject() instanceof BaseEntity) {
                    BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
                    if (Objects.isNull(baseEntity.getCreateTime())) {
                        baseEntity.setCreateTime(new Date());
                    }
                    baseEntity.setUpdateTime(baseEntity.getCreateTime());
                    LoginUser loginUser = SecurityUtils.getLoginUser();
                    if (loginUser != null) {
                        baseEntity.setCreateBy(this.getUserKey(loginUser));
                        baseEntity.setUpdateBy(baseEntity.getCreateBy());
                    }
                }else if(metaObject.getOriginalObject() instanceof AddBaseEntity) {
                    AddBaseEntity addBaseEntity = (AddBaseEntity) metaObject.getOriginalObject();
                    if(Objects.isNull(addBaseEntity.getCreateTime())){
                        addBaseEntity.setCreateTime(new Date());
                    }
                    LoginUser loginUser = SecurityUtils.getLoginUser();
                    if (loginUser != null) {
                        addBaseEntity.setCreateBy(this.getUserKey(loginUser));
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
                baseEntity.setUpdateTime(new Date());
                LoginUser loginUser = SecurityUtils.getLoginUser();
                if(loginUser != null){
                    baseEntity.setUpdateBy(this.getUserKey(loginUser));
                }
            }
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户唯一标识
     * @param loginUser
     * @return
     */
    private String getUserKey(LoginUser loginUser){
        if(loginUser.getSystemType() == SystemType.ADMIN){
            return "sys:" + loginUser.getSysUser().getUserId();
        }else{
            return loginUser.getAppUser().getId().toString();
        }
    }


}
