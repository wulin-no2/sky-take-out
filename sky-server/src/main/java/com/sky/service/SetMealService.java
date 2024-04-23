package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;


public interface SetMealService {
    void addSetMeal(SetmealDTO setmealDTO);

    PageResult pageSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);
}
