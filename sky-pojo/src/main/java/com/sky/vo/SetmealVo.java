package com.sky.vo;

import com.sky.entity.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SetmealVo implements Serializable {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String description;
    private String image;
    private String name;
    private BigDecimal price;
    private Integer status;
    private LocalDateTime updateTime;
    private List<SetmealDish> setmealDishes;
}
