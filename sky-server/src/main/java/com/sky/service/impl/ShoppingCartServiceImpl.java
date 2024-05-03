package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //check if the dish to be added exists
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        // get userId from ThreadLocal;
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        // if exists, number +1;
        if (list != null && !list.isEmpty()){
            ShoppingCart shoppingCart1 = list.get(0);
            shoppingCart1.setNumber(shoppingCart1.getNumber() + 1);
            shoppingCartMapper.updateNumber(shoppingCart1);
            return;
        }
        // if not, add it to database;
        Long dishId = shoppingCart.getDishId();
        if (dishId!=null){
            // add dish
            Dish dish = dishMapper.getDishById(dishId);

            shoppingCart.setName(dish.getName());
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setAmount(dish.getPrice());
        }else {
            //add setmeal
            Setmeal setmeal= setMealMapper.getSetmealById(shoppingCart.getSetmealId());
            shoppingCart.setAmount(setmeal.getPrice());
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setImage(setmeal.getImage());
        }
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);
    }

    @Override
    public List<ShoppingCart> list() {
        // get user id
        Long currentId = BaseContext.getCurrentId();
        // get list from mapper
        ShoppingCart shoppingCart = ShoppingCart
                .builder()
                .userId(currentId)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    @Override
    public void clean() {
        // get user id
        Long currentId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteBatchByUserId(currentId);
    }

    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        //check if the dish to be added exists
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        // get userId from ThreadLocal;
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if (list != null && !list.isEmpty()){
            // if the number > 1, number - 1;
            ShoppingCart item = list.get(0);
            Integer num = item.getNumber();
            if (num > 1){
                item.setNumber(num - 1);
                shoppingCartMapper.updateNumber(item);
            }
            // if the number is 1, delete it;
            else{
                shoppingCartMapper.deleteById(item.getId());
            }
        }


    }
}
