package com.moe.admin.domain.vo.product;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class ProductGroupDictVO implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private List<Dict> dictList;

    @Data
    public static class Dict implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 字典ID
         */
        private Integer id;

        /**
         * 字典名称
         */
        private String dictName;

        /**
         * 子字典列表，支持递归嵌套
         */
        private List<Dict> dictList;
    }
}
