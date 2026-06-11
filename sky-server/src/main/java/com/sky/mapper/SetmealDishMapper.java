package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SetmealDishMapper {
    List<Long> getById(@Param("ids") List<Long> ids);

    void insertBatch(@Param("setmealDishes") List<SetmealDish> setmealDishes);

    @Delete("delete from sky_take_out.setmeal_dish where setmeal_id=#{id}")
    void deleteById(Long id);

    @Delete("delete from sky_take_out.setmeal_dish where setmeal_id=#{setmealId}")
    void getBySetmealId(Long setmealId);
}
