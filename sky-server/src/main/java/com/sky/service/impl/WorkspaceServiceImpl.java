package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.*;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private ReportMapper reportMapper;

    /**
     * get turnover based on time range
     * @param begin
     * @param end
     * @return
     */
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        /**
         * turnover:The total amount completed on the day
         * valid order：The number of orders completed on the day
         * order complete rate：valid order / total order
         * average amount：turnover / valid order
         * new users：new users on the day
         */

        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);

        //get total order count
        Integer totalOrderCount = reportMapper.getOrderCountByMap(map);
        map.put("status", Orders.COMPLETED);

        //turnover
        BigDecimal turnover = reportMapper.getTurnoverByMap(map);
        turnover = turnover == null? BigDecimal.valueOf(0.0) : turnover;

        //valid order
        Integer validOrderCount = reportMapper.getOrderCountByMap(map);

        double unitPrice = 0.0;

        double orderCompletionRate = 0.0;
        if(totalOrderCount != 0 && validOrderCount != 0){
            //order complete rate
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            //average amount
            unitPrice = turnover.doubleValue() / validOrderCount;
        }

        //new user
        Integer newUsers = reportMapper.getNewUser(begin, end);

        return BusinessDataVO.builder()
                .turnover(turnover.doubleValue())
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }


    /**
     * get order overview
     *
     * @return
     */
    public OrderOverViewVO getOrderOverView() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
        map.put("status", Orders.TO_BE_CONFIRMED);

        //waiting orders
        Integer waitingOrders = reportMapper.getOrderCountByMap(map);
        //to be delivery
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = reportMapper.getOrderCountByMap(map);

        //completed
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = reportMapper.getOrderCountByMap(map);

        //canceled
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = reportMapper.getOrderCountByMap(map);

        //total order
        map.put("status", null);
        Integer allOrders = reportMapper.getOrderCountByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    /**
     * dishes overview
     *
     * @return
     */
    public DishOverViewVO getDishOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = reportMapper.countDishByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = reportMapper.countDishByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * setmeal overview
     *
     * @return
     */
    public SetmealOverViewVO getSetmealOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = reportMapper.countSetmealByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = reportMapper.countSetmealByMap(map);

        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }
}
