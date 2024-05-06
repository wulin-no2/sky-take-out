package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderDetailsVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
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

    /**
     * admin confirm order
     * @param ordersConfirmDTO
     * @return
     */
    @PutMapping("/confirm")
    @ApiOperation("admin confirm order")
    public Result confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        log.info("admin confirm order{}",ordersConfirmDTO.getId());
        orderService.confirmOrder(ordersConfirmDTO);
        return Result.success();
    }

    /**
     * admin complete Order id
     * @param id
     * @return
     */
    @PutMapping("/complete/{id}")
    @ApiOperation("admin complete Order")
    public Result completeOrder(@PathVariable Long id){
        log.info("admin complete Order id: {}",id);
        orderService.completeOrder(id);
        return Result.success();
    }

    /**
     *
     * @param ordersRejectionDTO
     * @return
     */
    @PutMapping("/rejection")
    @ApiOperation("admin rejects order id")
    public Result rejectOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO){
        log.info("admin rejects order id: {}",ordersRejectionDTO.getId());
        orderService.rejectOrder(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * admin delivery Order
     * @param id
     * @return
     */
    @PutMapping("/delivery/{id}")
    @ApiOperation("admin delivery Order")
    public Result deliveryOrder(@PathVariable Long id){
        log.info("admin delivery Order id{}",id);
        orderService.deliveryOrder(id);
        return Result.success();
    }
    @GetMapping("/details/{id}")
    @ApiOperation("admin check order details")
    public Result<OrderVO> orderDetails(@PathVariable Long id){
        log.info("admin check order details: id {}",id);
        OrderVO orderVO = orderService.orderDetails(id);
        return Result.success(orderVO);
    }
}
