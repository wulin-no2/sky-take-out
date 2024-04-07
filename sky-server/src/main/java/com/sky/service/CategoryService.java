package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {
    void addCategory(CategoryDTO categoryDTO);

    PageResult listCategoryByPage(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void setCategoryStatus(Integer status, Long id);

    void deleteCategory(Long id);
}
