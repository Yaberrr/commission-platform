package com.moe.product.domain.dto;

import com.moe.platform.dto.PlatformBaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 取消收藏
 *
 * @author liang.lu
 * 2025/4/14 11:04:00
 */
@Data
public class CancelFavoriteDTO extends PlatformBaseDTO {
    //商品id列表
    private List<String> productIds;
}
