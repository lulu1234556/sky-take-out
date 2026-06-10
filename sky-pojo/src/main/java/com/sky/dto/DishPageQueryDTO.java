package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DishPageQueryDTO implements Serializable {
    private int page;//必须
    private int pageSize;
    private String name;
    private Integer categoryId;//非必须，可能为null，不能使用int
    private Integer status;
}
