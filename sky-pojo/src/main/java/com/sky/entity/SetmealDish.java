package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealDish {
    private Integer copies;
    private Long dishId;
    private Long id;
    private String name;
    private BigDecimal price;
    private Long setmealId;
}
