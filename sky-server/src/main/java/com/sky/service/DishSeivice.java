package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVo;

import java.util.List;

public interface DishSeivice {
    void addDish(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void delectBatch(List<Long> ids);

    DishVo getById(Long id);

    void update(DishDTO dishDTO);

    void startOrStop(Integer status, Long id);
}
