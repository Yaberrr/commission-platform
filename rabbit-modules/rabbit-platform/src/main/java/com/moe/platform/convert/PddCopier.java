package com.moe.platform.convert;

import com.moe.platform.domain.vo.PddGoodsListItemVO;
import com.moe.platform.vo.ProductDetailVO;
import com.moe.platform.vo.ProductVO;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 拼多多属性复制mapper
 * @author tangyabo
 * @date 2025/3/27
 */
@Mapper
public interface PddCopier {

    PddCopier INSTANCE = Mappers.getMapper(PddCopier.class);

    PddGoodsListItemVO toItem(PddDdkGoodsDetailResponse.GoodsDetailResponseGoodsDetailsItem detailsItem);

    ProductDetailVO toDetailVo(ProductVO productVO);

}
