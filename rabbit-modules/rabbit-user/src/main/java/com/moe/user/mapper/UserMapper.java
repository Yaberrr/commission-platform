package com.moe.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moe.common.core.domain.user.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tangyabo
 * @date 2025/3/12
 */
public interface UserMapper extends BaseMapper<User> {

    //查询所有元宝码
    @Select("SELECT yb_code FROM rb_user")
    List<Integer> selectAllYbCode();

}
