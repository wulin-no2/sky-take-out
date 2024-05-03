package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * orders
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    /**
     * orders status
     */
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;

    /**
     * pay status
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private static final long serialVersionUID = 1L;

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
}
