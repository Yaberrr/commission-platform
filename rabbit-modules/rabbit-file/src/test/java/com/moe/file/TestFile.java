package com.moe.file;

import com.moe.file.service.ISysFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 京东api测试
 * @author tangyabo
 * @date 2025/3/6
 */
@SpringBootTest(classes = MoeFileApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestFile {

    @Autowired
    private ISysFileService sysFileService;

    @Test
    public void testUpload() {
//        sysFileService.uploadFile();
    }

}
