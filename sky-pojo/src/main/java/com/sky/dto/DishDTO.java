package com.sky.dto;

import com.sky.entity.DishFlavor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDTO implements Serializable {
    private Long categoryId;
    private String description;
    private List<DishFlavor> flavors=new ArrayList<>();
    private Long id;
    private String image;
    private String name;
    private BigDecimal price;
    private Integer status;
}
