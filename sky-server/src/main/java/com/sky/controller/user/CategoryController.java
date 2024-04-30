package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "user category interfaces")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * select category
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("select category")
    public Result<List<Category>> list(Integer type) {
        List<Category> list = categoryService.getCategoryByType(type);
        return Result.success(list);
    }
}