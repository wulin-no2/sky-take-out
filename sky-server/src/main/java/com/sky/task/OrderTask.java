package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * scheduled tasks
 */
@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * method to handle timeout orders
     */
    @Scheduled(cron = "0 * * * * ?")// once a min
//    @Scheduled(cron = "0/5 * * * * ?") // 5s once
    public void timeOutOrder(){
        log.info("timeOut Order cancel {}", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().minusMinutes(15);
        // query all the orders that status == 1 and now - 15min > orderTime
        List<Orders> list = orderMapper.listByStatusAndOrderTime(Orders.PENDING_PAYMENT, time);
        // set status and cancel time and cancel reason
        if (list != null && !list.isEmpty()){
            for (Orders order: list){
                order.setStatus(Orders.CANCELLED);
                order.setCancelTime(LocalDateTime.now());
                order.setCancelReason("Order time out.");
                orderMapper.update(order);
            }
        }
    }

    /**
     * process delivering orders
     */
    @Scheduled(cron = "0 0 1 * * ?")// 1am everyday
//    @Scheduled(cron = "1/5 * * * * ?") // 5s once
    public void noConfirmOrder(){
        log.info("no confirm order{}",LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().minusMinutes(120);
        // query all the orders that status == 1 and now - 60min > orderTime
        List<Orders> list = orderMapper.listByStatusAndOrderTime(Orders.DELIVERY_IN_PROGRESS, time);
        // set status and cancel time and cancel reason
        if (list != null && !list.isEmpty()){
            for (Orders order: list){
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
