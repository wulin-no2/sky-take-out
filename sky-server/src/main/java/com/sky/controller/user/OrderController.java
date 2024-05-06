package com.sky.controller.user;

import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;

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

}
