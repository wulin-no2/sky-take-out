package com.sky.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderStatisticsVO implements Serializable {
    //orders to be confirmed
    private Integer toBeConfirmed;

    //orders already confirmed
    private Integer confirmed;

    //orders delivery in progress
    private Integer deliveryInProgress;
}
