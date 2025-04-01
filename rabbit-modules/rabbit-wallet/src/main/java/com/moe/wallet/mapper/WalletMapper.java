package com.moe.wallet.mapper;

import com.moe.common.core.domain.user.Wallet;
import com.moe.common.core.mapper.BaseMapperPlus;
import com.moe.wallet.domain.bo.WalletUpdateBO;
import org.apache.ibatis.annotations.Select;

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

    int updateByBO(WalletUpdateBO bo);
}
