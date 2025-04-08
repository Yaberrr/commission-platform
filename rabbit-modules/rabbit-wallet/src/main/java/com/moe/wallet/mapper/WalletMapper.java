package com.moe.wallet.mapper;

import cn.hutool.core.date.DateTime;
import com.moe.common.core.domain.user.Wallet;
import com.moe.common.core.mapper.BaseMapperPlus;
import com.moe.wallet.domain.bo.WalletUpdateBO;
import com.moe.wallet.domain.vo.MyCommissionDetailVO;
import com.moe.wallet.domain.vo.MyCommissionVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
public interface WalletMapper extends BaseMapperPlus<WalletMapper, Wallet, Wallet> {

    /**
     * 加锁查询钱包
     * @param userId
     */
    @Select("SELECT * FROM rb_wallet WHERE user_id = #{userId} FOR UPDATE")
    Wallet selectByUserIdForUpdate(Long userId);

    /**
     * 数量更新
     * @param bo
     * @return
     */
    int updateByBO(WalletUpdateBO bo);

    /**
     * 我的收益
     */
    MyCommissionVO selectMyCommission(Long userId);

    /**
     * 我的收益明细
     *
     * @param userId    用户id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<MyCommissionDetailVO.Unit.Row> selectMyCommissionDetail(@Param("userId") Long userId,
                                                                 @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
