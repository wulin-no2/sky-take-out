package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {

    void addSetMealDishes(List<SetmealDish> setmealDishes);
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getSetmealDishesBySetmealId(Long id);

    void deleteBySetmealId(Long id);
}
