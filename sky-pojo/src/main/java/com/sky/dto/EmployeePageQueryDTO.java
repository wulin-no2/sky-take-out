package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeePageQueryDTO implements Serializable {

    //employee name
    private String name;

    //page
    private int page;

    //pageSize
    private int pageSize;

}
