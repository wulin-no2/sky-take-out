package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.ArrayList;
import java.util.List;

public interface DishService {
    void addDish(DishDTO dishDTO);

    PageResult getDishByPage(DishPageQueryDTO dishPageQueryDTO);

    List<Dish> getDishByCategoryId(Long categoryId);

    DishVO getDishById(Long id);

    void updateStatus(Integer status, Long id);

    void updateDish(DishDTO dishDTO);

    void deleteBatch(ArrayList<Long> ids);
}
