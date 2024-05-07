package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportVO implements Serializable {

    //date，separate with ","，etc: 2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    //order count everyday，separate with ","，etc:260,210,215
    private String orderCountList;

    //valid order count everyday，separate with ","，etc:260,210,215
    private String validOrderCountList;

    //total order count
    private Integer totalOrderCount;

    //total valid order count
    private Integer validOrderCount;

    //order complete rate
    private Double orderCompletionRate;

}
