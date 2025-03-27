package com.moe.platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moe.common.core.enums.platform.PlatformDictType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.platform.dto.product.PlatformProductDTO;
import com.moe.platform.dto.product.PlatformParam;
import com.moe.platform.dto.product.ProductDetailDTO;
import com.moe.platform.dto.product.ProductSearchDTO;
import com.moe.platform.service.impl.PddProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tangyabo
 * @date 2025/3/6
 */
@SpringBootTest(classes = MoePlatformApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPDD {

    @Autowired
    private PddProductService pddProductService;

    @Test
    public void testList() throws JsonProcessingException {
        PlatformProductDTO body = new PlatformProductDTO();
        body.setPageNum(1);
        body.setPageSize(50);
        PlatformParam param = new PlatformParam();
        param.setPlatformType(PlatformType.PDD);
        param.setDictType(PlatformDictType.LABEL);
        param.setDictValue("15");
        System.out.println(new ObjectMapper().writeValueAsString(pddProductService.productList(body,param)));
    }

    @Test
    public void testSearch() throws JsonProcessingException {
        ProductSearchDTO body = new ProductSearchDTO();
        body.setPageNum(1);
        body.setPageSize(50);
        body.setKeyword("aaaa");
        System.out.println(new ObjectMapper().writeValueAsString(pddProductService.productSearch(body)));
    }

    @Test
    public void testDetail() {
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setProductId("E9z2OUuET8xv4xnBwfDegsfxPvNaZYbW_JqUnwOTBD");
        pddProductService.productDetail(dto);
    }

}
