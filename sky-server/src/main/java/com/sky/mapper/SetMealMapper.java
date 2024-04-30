package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

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
    List<Setmeal> list(Setmeal setmeal);
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long id);
}
