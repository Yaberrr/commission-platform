package com.moe.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.product.ProductGroupDTO;
import com.moe.admin.domain.dto.product.ProductGroupUpdateDTO;
import com.moe.admin.domain.vo.product.ProductGroupDetailVO;
import com.moe.admin.domain.vo.product.ProductGroupDictVO;
import com.moe.admin.domain.vo.product.ProductGroupVO;
import com.moe.admin.mapper.PlatformDictMapper;
import com.moe.admin.mapper.ProductGroupMapper;
import com.moe.admin.service.ProductGroupService;
import com.moe.common.core.domain.platform.PlatformDict;
import com.moe.common.core.domain.product.ProductGroup;
import com.moe.common.core.enums.platform.PlatformType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductGroupServiceImpl implements ProductGroupService {

    @Autowired
    private ProductGroupMapper productGroupMapper;

    @Autowired
    private PlatformDictMapper platformDictMapper;

    @Override
    public Page<ProductGroupVO> selectProductGroupVOByDTO(IPage page, ProductGroupDTO productGroupDTO) {
        return productGroupMapper.selectProductGroupVOByDTO(page, productGroupDTO);
    }

    @Override
    public ProductGroupDetailVO selectProductGroupDetailById(Long id) {
        ProductGroupDetailVO productGroupDetailVO = new ProductGroupDetailVO();
        ProductGroup productGroup = productGroupMapper.selectById(id);
        BeanUtils.copyProperties(productGroup, productGroupDetailVO);
        List<PlatformDict> platformDictList = platformDictMapper.selectBatchIds(productGroup.getPlatformDictIds());
        productGroupDetailVO.setPlatformDicts(platformDictList);
        return productGroupDetailVO;
    }

    @Override
    public int addProductGroup(ProductGroupUpdateDTO productGroupUpdateDTO) {
        List<Integer> platformCodeList = platformDictMapper.selectBatchIds(productGroupUpdateDTO.getPlatformDictIds())
                .stream()
                .map(PlatformDict::getPlatformType)
                .filter(Objects::nonNull)
                .map(PlatformType::getCode)
                .distinct()
                .collect(Collectors.toList());

        ProductGroup productGroup = new ProductGroup();
        BeanUtils.copyProperties(productGroupUpdateDTO, productGroup);
        productGroup.setPlatformTypes(platformCodeList);

        return productGroupMapper.insert(productGroup);
    }

    @Override
    public int editProductGroup(ProductGroupUpdateDTO productGroupUpdateDTO) {
        List<Integer> platformCodeList = platformDictMapper.selectBatchIds(productGroupUpdateDTO.getPlatformDictIds())
                .stream()
                .map(PlatformDict::getPlatformType)
                .filter(Objects::nonNull)
                .map(PlatformType::getCode)
                .distinct()
                .collect(Collectors.toList());

        ProductGroup productGroup = new ProductGroup();
        BeanUtils.copyProperties(productGroupUpdateDTO, productGroup);
        productGroup.setPlatformTypes(platformCodeList);
        return productGroupMapper.updateById(productGroup);
    }

    @Override
    public int deleteProductGroup(List<Long> ids) {
        return productGroupMapper.deleteBatchIds(ids);
    }

    @Override
    public List<ProductGroupDictVO> getAllProductGroupDictVO() {
        List<PlatformDict> platformDictList = platformDictMapper.selectList(null);

        return platformDictList.stream()
                .collect(Collectors.groupingBy(PlatformDict::getPlatformType))
                .entrySet().stream()
                .map(entry -> {
                    ProductGroupDictVO vo = new ProductGroupDictVO();
                    vo.setPlatformName(entry.getKey().getDesc());
                    vo.setPlatformType(entry.getKey().getCode());

                    List<ProductGroupDictVO.PlatformDictType> dictTypeList = entry.getValue().stream()
                            .collect(Collectors.groupingBy(PlatformDict::getDictType))
                            .entrySet().stream()
                            .map(dictEntry -> {
                                ProductGroupDictVO.PlatformDictType dictType = new ProductGroupDictVO.PlatformDictType();
                                dictType.setDictType(dictEntry.getKey().getCode());
                                dictType.setDictName(dictEntry.getKey().getDesc());

                                List<ProductGroupDictVO.Dict> dictList = dictEntry.getValue().stream()
                                        .map(dict -> {
                                            ProductGroupDictVO.Dict dictVO = new ProductGroupDictVO.Dict();
                                            dictVO.setId(dict.getId());
                                            dictVO.setDictName(dict.getDictText());
                                            return dictVO;
                                        })
                                        .collect(Collectors.toList());

                                dictType.setDictList(dictList);
                                return dictType;
                            })
                            .collect(Collectors.toList());
                    vo.setDictTypeList(dictTypeList);
                    return vo;

                })
                .collect(Collectors.toList());
    }
}
