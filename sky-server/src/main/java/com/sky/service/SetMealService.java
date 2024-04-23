package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;


public interface SetMealService {
    void addSetMeal(SetmealDTO setmealDTO);

    PageResult pageSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);

    void updateStatus(Integer status, Long id);

    SetmealVO getSetmealById(Long id);
}
