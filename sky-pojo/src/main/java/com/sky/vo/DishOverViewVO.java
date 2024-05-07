package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * dish overview
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishOverViewVO implements Serializable {
    // sold volume
    private Integer sold;

    // stop sale volume
    private Integer discontinued;
}
