package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "set meal interfaces")
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    /**
     * add set meal
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "add set meal")
    public Result addSetMeal(@RequestBody SetmealDTO setmealDTO){
        log.info("add set meal{}",setmealDTO.getName());
        setMealService.addSetMeal(setmealDTO);
        return Result.success();
    }

    /**
     * setmeal page
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "setmeal page")
    public Result<PageResult> pageSetMeal(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("setmeal page{}",setmealPageQueryDTO.getName());
        PageResult pageResult = setMealService.pageSetMeal(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * modify status of setMeal
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "modify status of setMeal")
    public Result updateStatus(@PathVariable Integer status, Long id){
        log.info("modify status of setMeal{}",status==1?"on sale":"stop sale");
        setMealService.updateStatus(status,id);
        return Result.success();
    }

    /**
     * get set meal by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "get set meal by id")
    public Result<SetmealVO> getSetmealById(@PathVariable Long id){
        log.info("get set meal by id{}",id);
        SetmealVO setmeal = setMealService.getSetmealById(id);
        return Result.success(setmeal);
    }

    /**
     * delete set meal in batch
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("delete set meal in batch")
    public Result deleteSetmealInBatch(@RequestParam ArrayList<Long> ids){
        log.info("delete set meal in batch{}",ids);
        setMealService.deleteInBatch(ids);
        return Result.success();
    }

    /**
     * update set meal
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation(value = "update set meal")
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("update set meal{}",setmealDTO.getName());
        setMealService.update(setmealDTO);
        return Result.success();
    }
}
