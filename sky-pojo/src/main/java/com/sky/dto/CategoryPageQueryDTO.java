package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryPageQueryDTO implements Serializable {

    //page
    private int page;

    //pageSize
    private int pageSize;

    //category name
    private String name;

    //type 1 dish category 2 set category
    private Integer type;

}
