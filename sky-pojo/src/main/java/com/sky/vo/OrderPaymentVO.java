package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentVO implements Serializable {

    private String nonceStr; //random string
    private String paySign; //sign
    private String timeStamp; //time stamp
    private String signType; //sign algorithm
    private String packageStr; //identical prepay_id parameter returned from api

}
