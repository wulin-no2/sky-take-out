package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("AdminOrderController")
@Slf4j
@Api(tags = "admin order interfaces")
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    /**
     * page Orders
     * @return order PageResult
     */
    @GetMapping("/conditionSearch")
    public Result<PageResult> pageOrders(OrdersPageQueryDTO ordersPageQueryDTO){
        log.info("page orders and return in page,{}",ordersPageQueryDTO);
        PageResult pageResult = orderService.pageOrders(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * orders statistics
     * @return
     */
    @GetMapping("/statistics")
    @ApiOperation("orders statistics")
    public Result<OrderStatisticsVO> statisticOrder(){
        log.info("orders statistics.");
        OrderStatisticsVO orderStatisticsVO = orderService.statisticsOrder();
        return Result.success(orderStatisticsVO);
    }

    /**
     * admin cancel Order
     * @param ordersCancelDTO
     * @return
     */
    @PutMapping("/cancel")
    @ApiOperation("cancel Order in admin")
    public Result cancelOrder(@RequestBody OrdersCancelDTO ordersCancelDTO){
        log.info("cancel Order id: {}",ordersCancelDTO.getId());
        orderService.adminCancelOrder(ordersCancelDTO);
        return Result.success();
    }
    @PutMapping("/confirm")
    @ApiOperation("admin confirm order")
    public Result confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        log.info("admin confirm order{}",ordersConfirmDTO.getId());
        orderService.confirmOrder(ordersConfirmDTO);
        return Result.success();
    }
}
