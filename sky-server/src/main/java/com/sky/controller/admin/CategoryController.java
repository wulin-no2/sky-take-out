package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("add new category");
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }
}
