package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface SetmealMapper {

    @Select("select * from setmeal where id=#{id}")
    Setmeal getById(Long id);


    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    Page<SetmealVo> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @Delete("delete from setmeal where id=#{id}")
    void deleteById(Long id);

    SetmealVo getByIdWithDish(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
}

