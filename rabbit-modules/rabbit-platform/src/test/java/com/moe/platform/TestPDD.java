package com.moe.platform;

import com.moe.common.core.enums.platform.PlatformType;
import com.moe.platform.MoePlatformApplication;
import com.moe.platform.body.SearchBody;
import com.moe.platform.body.SearchParam;
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
    public void testSearch(){
        SearchBody body = new SearchBody();
        body.setPageNo(1);
        body.setPageSize(50);
        SearchParam param = new SearchParam();
        param.setPlatformType(PlatformType.PDD);
        pddProductService.search(body,param);
    }

}
