package com.moe.admin.domain.vo.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class ProductGroupVO {

    private Long id;

    private String groupName;

    private String platformTypesStr;

    private List<Integer> platformTypes;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void setPlatformTypesStr(String platformTypesStr) {
        this.platformTypesStr = platformTypesStr;
        if (platformTypesStr != null) {
            try {
                this.platformTypes = objectMapper.readValue(platformTypesStr, List.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPlatformTypes(List<Integer> platformTypes) {
        this.platformTypes = platformTypes;
        if (platformTypes != null) {
            try {
                this.platformTypesStr = objectMapper.writeValueAsString(platformTypes);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
