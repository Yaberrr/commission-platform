package com.moe.admin.domain.vo.product;


import lombok.Data;

import java.util.List;

@Data
public class ProductGroupDictVO {

    PlatformType platformType;

    @Data
    public static class PlatformType {
        private Integer platformType;
        List<DictType> dictTypeList;
    }

    @Data
    public static class DictType{
        private Integer dictType;
        private List<Dict> dictList;
    }

    @Data
    public static class Dict{
        private Long id;

        private String dictType;
    }
}
