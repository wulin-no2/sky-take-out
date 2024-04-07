package com.sky.dto;

import com.sky.entity.SetmealDish;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDTO implements Serializable {

    private Long id;

    private Long categoryId;

    //set name
    private String name;

    //set price
    private BigDecimal price;

    // 0:disable 1:enable
    private Integer status;

    private String description;

    private String image;

    //set dish relation
    private List<SetmealDish> setmealDishes = new ArrayList<>();

}
