package com.sky.mapper;

import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {
    void addDish(Dish dish, DishFlavor dishFlavor);
}
