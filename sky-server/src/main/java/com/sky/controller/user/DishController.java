package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "user dish interfaces")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * select dishes by category id
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("select dishes by category id")
    public Result<List<DishVO>> list(Long categoryId) {
        // redis key: dish_categoryId
        String key = "dish_" + categoryId;
        // query whether there is dish data in Redis
        List<DishVO> list =(List<DishVO>)redisTemplate.opsForValue().get(key);
        // if there is, return data from redis
        if (list!=null && !list.isEmpty()){
            return Result.success(list);
        }
        // if not, query data from database. then put data into Redis
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//dishes on sale
        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key,list);

        return Result.success(list);
    }

}
