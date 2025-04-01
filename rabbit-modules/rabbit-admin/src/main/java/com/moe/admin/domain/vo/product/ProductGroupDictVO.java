package com.moe.admin.domain.vo.product;


import lombok.Data;

import java.util.List;

@Data
public class ProductGroupDictVO {
    /**
     * 平台类型
     */
    private Integer platformType;
    /**
     * 平台类型名称
     */
    private String platformName;

    /**
     * 平台类型下的字典类型列表
     */
    List<PlatformDictType> dictTypeList;

    /**
     * 平台类型下的字典类型
     */
    @Data
    public static class PlatformDictType {
        /**
         * 字典类型
         */
        private Integer dictType;
        /**
         * 字典类型名称
         */
        private String dictName;

        /**
         * 每种类型下面的字典列表
         */
        private List<Dict> dictList;
    }

    @Data
    public static class Dict {
        private Long id;

        private String dictName;
    }
}
