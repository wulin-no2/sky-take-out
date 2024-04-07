package com.sky.dto;

import com.sky.entity.DishFlavor;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDTO implements Serializable {

    private Long id;
    // dish name
    private String name;
    // dish category id
    private Long categoryId;
    // dish price
    private BigDecimal price;
    // image
    private String image;
    // description
    private String description;
    // 0.stop sale 1.on sale
    private Integer status;
    // flavor
    private List<DishFlavor> flavors = new ArrayList<>();

}
