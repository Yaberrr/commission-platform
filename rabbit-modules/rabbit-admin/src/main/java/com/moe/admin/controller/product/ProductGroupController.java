package com.moe.admin.controller.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.product.ProductGroupDTO;
import com.moe.admin.domain.dto.product.ProductGroupUpdateDTO;
import com.moe.admin.domain.dto.rule.PolicyConfigDTO;
import com.moe.admin.domain.vo.product.ProductGroupDetailVO;
import com.moe.admin.domain.vo.product.ProductGroupVO;
import com.moe.admin.service.ProductGroupService;
import com.moe.common.core.web.controller.BaseController;
import com.moe.common.core.web.domain.AjaxResult;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/productGroup")
public class ProductGroupController extends BaseController {

    @Autowired
    private ProductGroupService productGroupService;

    @RequiresPermissions("admin:productGroup:list")
    @GetMapping("/list")
    public TableDataInfo list(ProductGroupDTO productGroupDTO)
    {
        Page<ProductGroupVO> list = productGroupService.selectProductGroupVOByDTO(TableSupport.buildPageRequest().buildPage(), productGroupDTO);
        return getDataTable(list);
    }

    @RequiresPermissions("admin:productGroup:query")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(productGroupService.selectProductGroupDetailById(id));
    }

    @RequiresPermissions("admin:productGroup:add")
    @PostMapping
    public AjaxResult add(@RequestBody ProductGroupUpdateDTO productGroupDTO)
    {
        return AjaxResult.success(productGroupService.addProductGroup(productGroupDTO));
    }

    @RequiresPermissions("admin:productGroup:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody ProductGroupUpdateDTO productGroupDTO)
    {
        return AjaxResult.success(productGroupService.editProductGroup(productGroupDTO));
    }

    @RequiresPermissions("admin:productGroup:delete")
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable Long[] ids)
    {
        return AjaxResult.success(productGroupService.deleteProductGroup(Arrays.asList(ids)));
    }

    @RequiresPermissions("admin:productGroup:query")
    @GetMapping("/detail")
    public AjaxResult detail()
    {
        return AjaxResult.success(productGroupService.getAllProductGroupDictVO());
    }

}
