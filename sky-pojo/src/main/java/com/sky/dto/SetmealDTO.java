package com.sky.dto;

import com.sky.entity.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SetmealDTO implements Serializable {
    private Long id;
    private Long categoryId;
    private String description;
    private String image;
    private String name;
    private BigDecimal price;
    private Integer status;
    private List<SetmealDish> setmealDishes;
}
