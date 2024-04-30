package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public interface SetMealService {
    void addSetMeal(SetmealDTO setmealDTO);

    PageResult pageSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);

    void updateStatus(Integer status, Long id);

    SetmealVO getSetmealById(Long id);

    void deleteInBatch(ArrayList<Long> ids);

    void update(SetmealDTO setmealDTO);

    List<Setmeal> list(Setmeal setmeal);
    List<DishItemVO> getDishItemById(Long id);
}
