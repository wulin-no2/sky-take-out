package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderDetailsVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;

import java.time.LocalDateTime;

public interface OrderService {
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    LocalDateTime payment(OrdersPaymentDTO ordersPaymentDTO);

    void reminder(Long id);

    PageResult pageOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statisticsOrder();

    void adminCancelOrder(OrdersCancelDTO ordersCancelDTO);

    void confirmOrder(OrdersConfirmDTO ordersConfirmDTO);

    void completeOrder(Long id);

    void rejectOrder(OrdersRejectionDTO ordersRejectionDTO);

    void deliveryOrder(Long id);

    OrderDetailsVO adminOrderDetails(Long id);
}
