package com.moe.product.service.impl;

import com.moe.common.core.domain.platform.PlatformDict;
import com.moe.common.core.domain.product.ProductGroup;
import com.moe.platform.dto.product.PlatformParam;
import com.moe.product.mapper.PlatformDictMapper;
import com.moe.product.mapper.ProductGroupMapper;
import com.moe.product.service.IProductGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tangyabo
 * @date 2025/3/21
 */
@Service
public class ProductGroupServiceImpl implements IProductGroupService {

    @Autowired
    private ProductGroupMapper productGroupMapper;
    @Autowired
    private PlatformDictMapper platformDictMapper;

    @Override
    public List<PlatformParam> platformParamList(Long groupId) {
        ProductGroup group = productGroupMapper.selectById(groupId);
        List<PlatformDict> dictList = platformDictMapper.selectBatchIds(group.getPlatformDictIds());
        return dictList.stream().map(dict -> {
            PlatformParam param = new PlatformParam();
            param.setPlatformType(dict.getPlatformType());
            param.setDictType(dict.getDictType());
            param.setDictValue(dict.getDictValue());
            return param;
        }).collect(Collectors.toList());
    }
}
