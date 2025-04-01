package com.moe.wallet.mapper;

import com.moe.common.core.domain.user.WalletRecord;
import com.moe.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Select;

/**
 * @author tangyabo
 * @date 2025/4/1
 */
public interface WalletRecordMapper extends BaseMapperPlus<WalletRecordMapper, WalletRecord, WalletRecord> {

    @Select("SELECT * FROM rb_wallet_record WHERE id = #{recordId} FOR UPDATE")
    WalletRecord selectByIdForUpdate(Long recordId);
}
