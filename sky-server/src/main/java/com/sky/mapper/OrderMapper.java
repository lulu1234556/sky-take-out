package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
    @Select("select * from orders where number = #{orderNumber} and user_id = #{userId}")
    Orders getById(String orderNumber, Long userId);

    void update(Orders order1);
}
