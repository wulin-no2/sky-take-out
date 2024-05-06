package com.sky.vo;

import com.sky.entity.OrderDetail;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data type returned when admin gets order details")
public class OrderDetailsVO {
    private Long id;

    //order number
    private String number;

    // orders status
    private Integer status;

    //user id
    private Long userId;

    //address id
    private Long addressBookId;

    //order time
    private LocalDateTime orderTime;

    //checkout time
    private LocalDateTime checkoutTime;

    //pay method 1 wechat，2 alipay
    private Integer payMethod;

    //pay status
    private Integer payStatus;

    //actual amount
    private BigDecimal amount;

    //remark
    private String remark;

    //user name
    private String userName;

    //phone
    private String phone;

    //address
    private String address;

    //consignee
    private String consignee;

    //cancel reason
    private String cancelReason;

    //rejection reason
    private String rejectionReason;

    //order cancel time
    private LocalDateTime cancelTime;

    //estimated delivery time
    private LocalDateTime estimatedDeliveryTime;

    //delivery status  1 now   0 specific time
    private Integer deliveryStatus;

    //delivery time
    private LocalDateTime deliveryTime;

    //pack fee
    private int packAmount;

    //tableware number
    private int tablewareNumber;

    //tableware status  1 provide according to order  0 choose number
    private Integer tablewareStatus;

    //order dishes
    private String orderDishes;

    //order detail
    private List<OrderDetail> orderDetailList;
}
