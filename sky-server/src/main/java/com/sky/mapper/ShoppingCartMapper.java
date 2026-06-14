package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ShoppingCartMapper {
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    @AutoFill(OperationType.UPDATE)
    @Update("update shopping_cart set number=#{number} where id=#{id} ")
    void updateNumber(ShoppingCart shoppingCart);

    void insert(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where user_id=#{id}")
    void clean(Long userId);


    @Delete("delete from shopping_cart where id=#{id}")
    void delete(Long id);

    @Delete("delete from shopping_cart where user_id=#{currentId}")
    void deleteByUserId(Long currentId);
}
