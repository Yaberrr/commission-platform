package com.moe.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.product.ProductGroupDTO;
import com.moe.admin.domain.dto.product.ProductGroupUpdateDTO;
import com.moe.admin.domain.vo.product.ProductGroupDetailVO;
import com.moe.admin.domain.vo.product.ProductGroupVO;
import com.moe.admin.mapper.PlatformDictMapper;
import com.moe.admin.mapper.ProductGroupMapper;
import com.moe.admin.service.IProductGroupService;
import com.moe.common.core.domain.platform.PlatformDict;
import com.moe.common.core.domain.product.ProductGroup;
import com.moe.common.core.enums.platform.PlatformDictType;
import com.moe.common.core.enums.platform.PlatformType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductGroupServiceImpl implements IProductGroupService {

    @Autowired
    private ProductGroupMapper productGroupMapper;

    @Autowired
    private PlatformDictMapper platformDictMapper;

    /**
     * 递归构建树形结构
     *
     * @param dicts    所有字典数据
     * @param dictType 当前字典类型
     * @param parentId 父节点ID
     * @return 子节点列表
     */
    private static List<Map<String, Object>> buildTree(List<PlatformDict> dicts, PlatformDictType dictType, Long parentId) {
        return dicts.stream().filter(dict -> dict.getDictType() == dictType && Objects.equals(dict.getParentId(), parentId)).map(dict -> {
            Map<String, Object> node = new HashMap<>();
            node.put("id", dict.getId());
            node.put("dictText", dict.getDictText());
            node.put("dictValue", dict.getDictValue());

            // 递归构建子节点
            List<Map<String, Object>> children = buildTree(dicts, dictType, dict.getId());

            // 只有当有子节点时才添加children字段
            if (!children.isEmpty()) {
                node.put("hasChild", 1);
                node.put("children", children);
            } else {
                node.put("hasChild", 0);
            }

            return node;
        }).collect(Collectors.toList());
    }

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
        List<Integer> platformCodeList = platformDictMapper.selectBatchIds(productGroupUpdateDTO.getPlatformDictIds()).stream().map(PlatformDict::getPlatformType).filter(Objects::nonNull).map(PlatformType::getCode).distinct().collect(Collectors.toList());

        ProductGroup productGroup = new ProductGroup();
        BeanUtils.copyProperties(productGroupUpdateDTO, productGroup);
        productGroup.setPlatformTypes(platformCodeList);

        return productGroupMapper.insert(productGroup);
    }

    @Override
    public int editProductGroup(ProductGroupUpdateDTO productGroupUpdateDTO) {
        List<Integer> platformCodeList = platformDictMapper.selectBatchIds(productGroupUpdateDTO.getPlatformDictIds()).stream().map(PlatformDict::getPlatformType).filter(Objects::nonNull).map(PlatformType::getCode).distinct().collect(Collectors.toList());

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
    public List<Map<String, Object>> getAllProductGroupDict() {
        List<PlatformDict> platformDictList = platformDictMapper.selectList(null);

        // 按平台类型分组
        Map<PlatformType, List<PlatformDict>> platformGroup = platformDictList.stream().collect(Collectors.groupingBy(PlatformDict::getPlatformType));

        List<Map<String, Object>> result = new ArrayList<>();

        // 构建每个平台类型下的树形结构
        platformGroup.forEach((platformType, platformDicts) -> {
            Map<String, Object> platformNode = new HashMap<>();
            platformNode.put("platformType", platformType);
            platformNode.put("platformName", platformType.getDesc());

            // 按dictType分组作为第一级子节点
            Map<PlatformDictType, List<PlatformDict>> typeGroup = platformDicts.stream().collect(Collectors.groupingBy(PlatformDict::getDictType));

            List<Map<String, Object>> typeNodes = new ArrayList<>();
            typeGroup.forEach((dictType, typeDicts) -> {
                Map<String, Object> typeNode = new HashMap<>();
                typeNode.put("id", dictType.getCode()); // 使用字典类型code作为ID
                typeNode.put("dictText", dictType.getDesc()); // 字典类型描述作为显示文本
                typeNode.put("dictValue", dictType.name()); // 字典类型名称作为值
                typeNode.put("hasChild", 1); // 标记有子节点

                // 构建该类型下的树形结构
                typeNode.put("children", buildTree(platformDicts, dictType, null));

                typeNodes.add(typeNode);
            });

            platformNode.put("children", typeNodes);
            result.add(platformNode);
        });

        return result;
    }

}
