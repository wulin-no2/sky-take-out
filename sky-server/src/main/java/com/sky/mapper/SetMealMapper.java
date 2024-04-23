package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface SetMealMapper {
    @AutoFill(value = OperationType.INSERT)
    void addSetmeal(Setmeal setMeal);

    Page<Setmeal> pageSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
    @Select("select * from setmeal where id=#{id}")
    Setmeal getSetmealById(Long id);

    void deleteInBatch(ArrayList<Long> ids);
}
