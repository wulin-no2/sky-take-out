package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    @AutoFill(value = OperationType.INSERT)
    void addDish(Dish dish);


    Page<Dish> getDishByPage(DishPageQueryDTO dishPageQueryDTO);
    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> getDishByCategoryId(Long categoryId);
    @Select("select * from dish where id = #{id}")
    Dish getDishById(Long id);
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);
}
