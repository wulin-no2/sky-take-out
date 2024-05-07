package com.sky.controller.user;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController("UserOrderController")
@RequestMapping("/user/order")
@Api(tags = "user order interfaces")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * user submit order
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("user submit order")
    public Result<OrderSubmitVO> submitOrder(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("user submit order{}",ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * user pay the order
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("user pay the order")
    public Result<String> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO){
        LocalDateTime estimatedTime = orderService.payment(ordersPaymentDTO);

        return Result.success(String.valueOf(estimatedTime));
    }

    /**
     * user reminder order.
     * @return
     */
    @GetMapping("/reminder/{id}")
    @ApiOperation("user reminder order")
    public Result reminder(@PathVariable Long id){
        log.info("user reminder order {}.",id);
        orderService.reminder(id);
        return Result.success();
    }

    /**
     * user get order details
     * @param id
     * @return
     */
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> orderDetails(@PathVariable String id){
        log.info("user get order details{}",id);
        OrderVO orderVO = orderService.orderDetails(id);
        return Result.success(orderVO);
    }
    @GetMapping("/historyOrders")
    @ApiOperation("user get historyOrders")
    public Result<PageResult> pageHistoryOrders(int page, int pageSize, Integer status) {
        log.info("user get historyOrders");
        PageResult pageResult = orderService.pageHistoryOrders(page, pageSize, status);
        return Result.success(pageResult);
    }

    /**
     * user cancel Order
     * @param id order id
     * @return
     */
    @PutMapping("/cancel/{id}")
    @ApiOperation("user cancel Order")
    public Result cancelOrder(@PathVariable Long id){
        log.info("cancel Order id: {}",id);
        orderService.userCancelOrder(id);
        return Result.success();
    }
    @PostMapping("/repetition/{id}")
    @ApiOperation("user repetition Order id")
    public Result repetition(@PathVariable Long id){
        log.info("user repetition Order id: {}",id);
        orderService.userRepetitionOrder(id);
        return Result.success();

    }
}
