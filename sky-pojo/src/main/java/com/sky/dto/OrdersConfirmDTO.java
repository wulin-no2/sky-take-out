package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersConfirmDTO implements Serializable {

    private Long id;
    //order status 1 to pay 2 to receive order 3 received order 4 on delivery 5 completed 6 cancelled 7 refund
    private Integer status;

}
