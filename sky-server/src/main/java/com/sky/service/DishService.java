package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    void addDish(DishDTO dishDTO);

    PageResult getDishByPage(DishPageQueryDTO dishPageQueryDTO);

    List<Dish> getDishByCategoryId(Long categoryId);
}
