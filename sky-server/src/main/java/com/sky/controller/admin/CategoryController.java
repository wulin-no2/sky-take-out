package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "category interfaces")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * add new category
     * @return
     */
    @PostMapping
    @ApiOperation(value = "add new category")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("add new category {}",categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation(value = "get categories by page")
    public Result<PageResult> listCategoryByPage(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("get categories by page {}",categoryPageQueryDTO);
        PageResult pageResult = categoryService.listCategoryByPage(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    @PutMapping
    @ApiOperation(value = "update category")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("update category {}",categoryDTO);
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    /**
     * set Category Status
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "set Category Status")
    public Result setCategoryStatus(@PathVariable Integer status, Long id){
        log.info("set Category Status {},{}",status,id);
        categoryService.setCategoryStatus(status,id);
        return Result.success();
    }

    /**
     * delete Category
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "delete Category")
    public Result deleteCategory(Long id){
        log.info("delete Category {}",id);
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
