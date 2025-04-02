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

/**
 * 产品组管理
 * <p>
 * 处理产品组的增删改查请求，包含权限控制和数据返回处理
 */
@RestController
@RequestMapping("/productGroup")
public class ProductGroupController extends BaseController {

    @Autowired
    private ProductGroupService productGroupService;

    /**
     * 分页查询产品组列表
     * @param productGroupDTO 查询条件参数对象
     * @return 分页表格数据（带分页信息）
     */
    @RequiresPermissions("admin:productGroup:list")
    @GetMapping("/list")
    public TableDataInfo list(ProductGroupDTO productGroupDTO)
    {
        Page<ProductGroupVO> list = productGroupService.selectProductGroupVOByDTO(TableSupport.buildPageRequest().buildPage(), productGroupDTO);
        return getDataTable(list);
    }

    /**
     * 获取产品组详细信息
     * @param id 产品组主键ID
     * @return 包含完整产品组详情的响应结果
     */
    @RequiresPermissions("admin:productGroup:query")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(productGroupService.selectProductGroupDetailById(id));
    }

    /**
     * 新增产品组
     * @param productGroupDTO 产品组数据传输对象
     * @return 操作结果响应
     */
    @RequiresPermissions("admin:productGroup:add")
    @PostMapping
    public AjaxResult add(@RequestBody ProductGroupUpdateDTO productGroupDTO)
    {
        return AjaxResult.success(productGroupService.addProductGroup(productGroupDTO));
    }

    /**
     * 修改产品组信息
     * @param productGroupDTO 产品组更新数据传输对象
     * @return 操作结果响应
     */
    @RequiresPermissions("admin:productGroup:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody ProductGroupUpdateDTO productGroupDTO)
    {
        return AjaxResult.success(productGroupService.editProductGroup(productGroupDTO));
    }

    /**
     * 批量删除产品组
     * @param ids 需要删除的产品组ID数组
     * @return 操作结果响应
     */
    @RequiresPermissions("admin:productGroup:delete")
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable Long[] ids)
    {
        return AjaxResult.success(productGroupService.deleteProductGroup(Arrays.asList(ids)));
    }

    /**
     * 获取产品组筛选条件字典
     * @return 包含层级字典结构的响应结果
     */
    @RequiresPermissions("admin:productGroup:query")
    @GetMapping("/conditions")
    public AjaxResult getProductGroupConditions()
    {
        return AjaxResult.success(productGroupService.getAllProductGroupDictVO());
    }

}
