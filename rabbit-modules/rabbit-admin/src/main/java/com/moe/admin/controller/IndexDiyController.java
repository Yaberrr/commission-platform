package com.moe.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.IndexDiySaveDTO;
import com.moe.admin.service.IIndexDiyService;
import com.moe.common.core.domain.IndexDiy;
import com.moe.common.core.domain.R;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 首页装修
 * @author tangyabo
 * @date 2025/4/11
 */
@RestController
@RequestMapping("/indexDiy")
public class IndexDiyController extends BaseController {

    @Autowired
    private IIndexDiyService indexDiyService;

    @Operation(description = "装修列表")
    @GetMapping("/list")
    public TableDataInfo<IndexDiy> indexDiyList() {
        Page<IndexDiy> list = indexDiyService.indexDiyList(TableSupport.buildPageRequest().buildPage());
        return getDataTable(list);
    }

    @Operation(description = "装修详情")
    @GetMapping("/detail")
    public R<IndexDiy> indexDiyDetail(Long id) {
        return R.ok(indexDiyService.indexDiyDetail(id));
    }

    @Operation(description = "保存装修")
    @PostMapping("/save")
    public R<?> saveIndexDiy(@RequestBody IndexDiySaveDTO dto){
        indexDiyService.saveIndexDiy(dto);
        return R.ok();
    }


}
