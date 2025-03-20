package com.moe.product;

import com.moe.common.core.enums.platform.PlatformDictType;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.platform.api.PlatformProductApi;
import com.moe.platform.body.SearchBody;
import com.moe.platform.body.SearchParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test
    public void testProduct(){
        SearchBody body = new SearchBody();
        body.setPageNo(1);
        body.setPageSize(50);
        SearchParam param = new SearchParam();
        param.setPlatformType(PlatformType.PDD);
        param.setTagType(PlatformDictType.LABEL);
        param.setTagValue("15");
        body.setParamList(Collections.singletonList(param));
        System.out.println(platformProductApi.search(body));
    }
}
