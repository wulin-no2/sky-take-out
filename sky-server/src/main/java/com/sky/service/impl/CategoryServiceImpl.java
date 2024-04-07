package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        // set status
        category.setStatus(StatusConstant.ENABLE);
        //set create, update time and user
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());
//        category.setCreateUser(BaseContext.getCurrentId());
        categoryMapper.addCategory(category);
    }

    /**
     * list Category By Page
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult listCategoryByPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        // get page result;
        Page<Category> categoryPage = categoryMapper.listCategoryByPage(categoryPageQueryDTO);
        long total = categoryPage.getTotal();
        List<Category> result = categoryPage.getResult();
        return new PageResult(total,result);
    }

    /**
     * update Category
     * @param categoryDTO
     */
    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        // add update info
//        category.setUpdateUser(BaseContext.getCurrentId());
//        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.updateCategory(category);
    }

    /**
     * set Category Status
     * @param status
     * @param id
     */
    @Override
    public void setCategoryStatus(Integer status, Long id) {
        // we can use common update method here:
        Category category = Category.builder()
                .status(status)
                .id(id)
                .build();
        categoryMapper.updateCategory(category);
    }

    /**
     * delete Category
     * @param id
     */
    @Override
    public void deleteCategory(Long id) {
        //TODO: if dish or set has this category, can't delete
        categoryMapper.deleteCategory(id);
    }

}
