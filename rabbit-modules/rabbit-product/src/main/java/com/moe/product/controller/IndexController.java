package com.moe.product.controller;

import com.moe.common.core.domain.R;
import com.moe.common.security.utils.SecurityUtils;
import com.moe.product.service.IIndexDiyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author tangyabo
 * @date 2025/4/14
 */
@Tag(name = "装修")
@RestController
@RequestMapping("/diy")
public class IndexController {

    @Autowired
    private IIndexDiyService indexDiyService;

    @Operation(description = "首页")
    @PostMapping("/index")
    public R<Map<String,Object>> getIndexDiy(){
        return R.ok(indexDiyService.getIndexDiy(SecurityUtils.getAppVersion()));
    }

}
