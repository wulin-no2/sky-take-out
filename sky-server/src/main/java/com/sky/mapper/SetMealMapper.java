package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetMealMapper {
    @AutoFill(value = OperationType.INSERT)
    void addSetmeal(Setmeal setMeal);
}
