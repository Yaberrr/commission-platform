package com.moe.product;

import com.moe.common.core.domain.product.ProductGroup;
import com.moe.common.core.enums.platform.PlatformDictType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.platform.api.PlatformProductApi;
import com.moe.platform.dto.PlatformProductDTO;
import com.moe.platform.dto.PlatformParam;
import com.moe.product.mapper.ProductGroupMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author tangyabo
 * @date 2025/3/20
 */
@SpringBootTest(classes = MoeProductApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestProduct {

    @Autowired
    private PlatformProductApi platformProductApi;
    @Autowired
    private ProductGroupMapper productGroupMapper;

    @Test
    public void testProduct(){
        PlatformProductDTO body = new PlatformProductDTO();
        body.setPageNum(1);
        body.setPageSize(50);
        PlatformParam param = new PlatformParam();
        param.setPlatformType(PlatformType.PDD);
        param.setDictType(PlatformDictType.LABEL);
        param.setDictValue("15");
        body.setParamList(Collections.singletonList(param));
        System.out.println(platformProductApi.list(body));
    }

    @Test
    public void testSelect(){
        ProductGroup group = new ProductGroup();
        group.setGroupName("test");
        group.setPlatformDictIds(Arrays.asList(1L,2L));
        productGroupMapper.insert(group);
    }
}
