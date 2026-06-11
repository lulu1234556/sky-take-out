package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DishMapper {

    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVo> list(DishPageQueryDTO dishPageQueryDTO);

    List<Dish> listByCategoryAndStatus(Dish dish);

    @Select("select * from dish where id=#{id}")
    Dish getById(Long id);

    @Delete("delete from dish where id=#{id}")
    void delectByid(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    Integer countBySetmealIdAndStatus(@Param("setmealId")Long id, @Param("status")Integer disable);
}
