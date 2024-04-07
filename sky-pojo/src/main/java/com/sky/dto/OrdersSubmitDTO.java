package com.sky.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersSubmitDTO implements Serializable {
    //address book id
    private Long addressBookId;
    //payment type
    private int payMethod;
    //remark
    private String remark;
    //estimated delivery time
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;
    //Delivery status 1.Send immediately 0.Select a specific time
    private Integer deliveryStatus;
    //Quantity of tableware
    private Integer tablewareNumber;
    //Tableware quantity status 1.Provided by meal size 0.Select specific quantity
    private Integer tablewareStatus;
    //packing fee
    private Integer packAmount;
    //total amount
    private BigDecimal amount;
}
