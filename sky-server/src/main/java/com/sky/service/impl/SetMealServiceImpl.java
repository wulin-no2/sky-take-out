package com.sky.service.impl;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    /**
     * add Set Meal
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void addSetMeal(SetmealDTO setmealDTO) {
        // create setmeal and fill the properties
        Setmeal setMeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setMeal);
        setMealMapper.addSetmeal(setMeal);

        // create set meal dishes;
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        Long id = setMeal.getId();
        if (setmealDishes!=null && !setmealDishes.isEmpty()){
            setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(id));
            setMealDishMapper.addSetMealDishes(setmealDishes);
            log.info("add set meal dishes{}",setmealDishes);
            }
        }



}
