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
    @GetMapping("/{id}")
    public R<IndexDiy> indexDiyDetail(@PathVariable("id") Long id) {
        return R.ok(indexDiyService.indexDiyDetail(id));
    }

    @Operation(description = "保存装修")
    @PostMapping
    public R<?> saveIndexDiy(@RequestBody IndexDiySaveDTO dto){
        indexDiyService.saveIndexDiy(dto);
        return R.ok();
    }

    @Operation(description = "取消发布")
    @PostMapping("/cancel")
    public R<?> cancelIndexDiy(Long id) {
        indexDiyService.cancelIndexDiy(id);
        return R.ok();
    }

    @Operation(description = "删除装修")
    @DeleteMapping
    public R<?> deleteIndexDiy(Long id) {
        indexDiyService.deleteIndexDiy(id);
        return R.ok();
    }


}
