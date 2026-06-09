package com.sky.mapper;

import org.apache.ibatis.annotations.Select;

public interface DishMapper {
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);
}
