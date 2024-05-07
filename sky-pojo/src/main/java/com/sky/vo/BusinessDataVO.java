package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据概览
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDataVO implements Serializable {

    private Double turnover;//turnover

    private Integer validOrderCount;//valid order count

    private Double orderCompletionRate;//order complete rate

    private Double unitPrice;//average unit amount

    private Integer newUsers;//new users

}
