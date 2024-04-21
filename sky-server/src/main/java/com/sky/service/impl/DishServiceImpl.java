package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    /**
     * add dish
     * @param dishDTO
     */
    @Override
    @Transactional
    public void addDish(DishDTO dishDTO) {
        // get dish:
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.addDish(dish);
//        get dish flavor:
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors!=null && !flavors.isEmpty()){
            flavors.forEach(flavor -> flavor.setDishId(id));
            dishFlavorMapper.addDishFlavors(flavors);
            log.info("dish flavor is:{}",flavors);
        }
    }

    @Override
    public PageResult getDishByPage(DishPageQueryDTO dishPageQueryDTO) {
        // use pageHelper to page DTO;
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        // get Page result with Dish objects;
        Page<Dish> dishPage = dishMapper.getDishByPage(dishPageQueryDTO);
        long total = dishPage.getTotal();
        List<Dish> result = dishPage.getResult();
        return new PageResult(total,result);
    }
}
