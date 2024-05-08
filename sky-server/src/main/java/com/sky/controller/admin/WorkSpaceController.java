package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * work space
 */
@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@Api(tags = "workspace interfaces")
public class WorkSpaceController {

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * work space data overview today
     * @return
     */
    @GetMapping("/businessData")
    @ApiOperation("work space data overview today")
    public Result<BusinessDataVO> businessData(){
        //get begin time
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        //get end time
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

        BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin, end);
        return Result.success(businessDataVO);
    }

    /**
     * get order overview
     * @return
     */
    @GetMapping("/overviewOrders")
    @ApiOperation("get order overview ")
    public Result<OrderOverViewVO> orderOverView(){
        return Result.success(workspaceService.getOrderOverView());
    }

    /**
     * get dishes overview
     * @return
     */
    @GetMapping("/overviewDishes")
    @ApiOperation("get dishes overview")
    public Result<DishOverViewVO> dishOverView(){
        return Result.success(workspaceService.getDishOverView());
    }

    /**
     * get setmeal overview
     * @return
     */
    @GetMapping("/overviewSetmeals")
    @ApiOperation("get setmeal overview")
    public Result<SetmealOverViewVO> setmealOverView(){
        return Result.success(workspaceService.getSetmealOverView());
    }
}
