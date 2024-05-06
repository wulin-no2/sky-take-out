package com.sky.controller.admin;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
