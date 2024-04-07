package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {

    //primary key
    private Long id;

    //type 1 dish category 2 set category
    private Integer type;

    //category name
    private String name;

    //sort
    private Integer sort;

}
