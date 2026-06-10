package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

public interface DishFlavorMapper {
    @AutoFill(OperationType.INSERT)
    void insertBatch(@Param("flavorList") List<DishFlavor> flavorList);

    @Delete("delete from dish_flavor where dish_id=#{id}")
    void delectByid(Long id);

    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> getById(Long id);
}
