package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //name
    private String name;

    //order id
    private Long orderId;

    private Long dishId;

    //set id
    private Long setmealId;

    //flavor
    private String dishFlavor;

    //number
    private Integer number;

    //amount
    private BigDecimal amount;

    private String image;
}
