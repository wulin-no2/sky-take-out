package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    @Autowired
    private CategoryMapper categoryMapper;
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

    /**
     * page SetMeal with pageHelper
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageSetMeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        // get page result;
        Page<Setmeal> pageResult = setMealMapper.pageSetMeal(setmealPageQueryDTO);
        long total = pageResult.getTotal();
        List<Setmeal> result = pageResult.getResult();
        return new PageResult(total,result);
    }

    /**
     * update set meal Status
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                .status(status)
                .id(id)
                .build();
        setMealMapper.update(setmeal);

    }

    /**
     * get Setmeal By Id
     * @param id
     * @return
     */
    @Override
    public SetmealVO getSetmealById(Long id) {
        // get set meal
        Setmeal setmeal = setMealMapper.getSetmealById(id);
        // get set meal dishes
        List<SetmealDish> list = setMealDishMapper.getSetmealDishesBySetmealId(id);
        // get category name
        Category category = categoryMapper.getCategoryByCategoryId(setmeal.getCategoryId());
        // set setmealVO
        SetmealVO setmealVO = SetmealVO.builder()
                .categoryName(category.getName())
                .setmealDishes(list)
                .build();
        BeanUtils.copyProperties(setmeal,setmealVO);
        return setmealVO;
    }

    @Override
    @Transactional
    public void deleteInBatch(ArrayList<Long> ids) {
        setMealMapper.deleteInBatch(ids);
        log.info("dish ids are {}",ids);
        ids.forEach(id->setMealDishMapper.deleteBySetmealId(id));
    }
}
