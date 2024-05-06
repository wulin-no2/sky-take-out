package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;

import java.time.LocalDateTime;

public interface OrderService {
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    LocalDateTime payment(OrdersPaymentDTO ordersPaymentDTO);

    void reminder(Long id);

    PageResult pageOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statisticsOrder();
}
