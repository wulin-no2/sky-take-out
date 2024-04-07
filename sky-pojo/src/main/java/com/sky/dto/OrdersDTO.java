package com.sky.dto;

import com.sky.entity.OrderDetail;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersDTO implements Serializable {

    private Long id;

    //order number
    private String number;

    //order status 1 to pay，2 to deliver，3 delivered，4 completed，5 cancelled
    private Integer status;

    //order client id
    private Long userId;

    //address id
    private Long addressBookId;

    //order time
    private LocalDateTime orderTime;

    //payment time
    private LocalDateTime checkoutTime;

    //payment type 1 weChat 2 Alipay
    private Integer payMethod;

    // amount actually received
    private BigDecimal amount;

    // remark
    private String remark;

    // user name
    private String userName;

    // phone
    private String phone;

    // address
    private String address;

    // Receiver
    private String consignee;

    private List<OrderDetail> orderDetails;

}
