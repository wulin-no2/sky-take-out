package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "dish interfaces")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * add new dish
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "add new dish")
    public Result addDish(@RequestBody DishDTO dishDTO){
        log.info("add new dish {}",dishDTO);
        dishService.addDish(dishDTO);
        // Clear cache data
        String key = "dish_" + dishDTO.getCategoryId();
        redisTemplate.delete(key);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation(value = "get dishes by page")
    public Result<PageResult> getDishByPage(DishPageQueryDTO dishPageQueryDTO){
        log.info("get dishes by page: {}",dishPageQueryDTO);
        PageResult pageResult = dishService.getDishByPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/list")
    @ApiOperation(value = "get dish by category id")
    public Result<List<Dish>> getDishByCategoryId(Long categoryId){
        log.info("get dish by category id,{}",categoryId);
        List<Dish> list = dishService.getDishByCategoryId(categoryId);
        return Result.success(list);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "get dish by id")
    public Result<DishVO> getDishById(@PathVariable Long id){
        log.info("get dish by id {}",id);
        DishVO dishVO = dishService.getDishById(id);
        return Result.success(dishVO);
    }
    @PostMapping("/status/{status}")
    @ApiOperation(value = "modify dish status")
    public Result updateStatus(@PathVariable Integer status, Long id){
        log.info("modify dish status{} for {}",status,id);
        dishService.updateStatus(status, id);
        // delete all the dish cache data
        clearCache("dish_*");
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation(value = "delete dishes by ids")
    public Result deleteDishes(@RequestParam ArrayList<Long> ids){
        log.info("delete dishes{}",ids);
        dishService.deleteBatch(ids);
        // delete all the dish cache data
        clearCache("dish_*");
        return Result.success();
    }
    @PutMapping
    @ApiOperation(value = "update dish")
    public Result updateDish(@RequestBody DishDTO dishDTO){
        dishService.updateDish(dishDTO);
        // delete all the dish cache data
        clearCache("dish_*");
        return Result.success();
    }
    private void clearCache(String pattern){
        // delete all the dish cache data
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
