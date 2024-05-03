package com.sky.service;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

import java.time.LocalDateTime;

public interface OrderService {
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    LocalDateTime payment(OrdersPaymentDTO ordersPaymentDTO);
}
