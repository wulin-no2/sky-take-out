package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单概览数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOverViewVO implements Serializable {
    //waiting orders
    private Integer waitingOrders;

    //delivered orders
    private Integer deliveredOrders;

    //completed orders
    private Integer completedOrders;

    //canceled orders
    private Integer cancelledOrders;

    //all orders
    private Integer allOrders;
}
