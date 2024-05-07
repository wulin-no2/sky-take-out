package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * setmeal overview
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealOverViewVO implements Serializable {
    // sold volume
    private Integer sold;

    // stop number
    private Integer discontinued;
}
