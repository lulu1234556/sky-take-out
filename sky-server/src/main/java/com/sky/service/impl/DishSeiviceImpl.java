package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishAMapper;
import com.sky.result.PageResult;
import com.sky.service.DishSeivice;
import com.sky.vo.DishVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishSeiviceImpl implements DishSeivice {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishAMapper setmealDishAMapper;

    @Override
    public void addDish(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);
        //口味可能有多条，单独插入
        List<DishFlavor> flavorList=dishDTO.getFlavors();
        if(flavorList!=null&&flavorList.size()>0){
            flavorList.forEach(flavor->flavor.setDishId(dish.getId()));
            dishFlavorMapper.insertBatch(flavorList);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVo> page=dishMapper.list(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    @Transactional
    public void delectBatch(List<Long> ids) {
//        起售中的菜品不能删除
//        被套餐关联的菜品不能删除
//        删除菜品后，关联的口味数据也需要删除掉
        for(Long id:ids){
            Dish dish=dishMapper.getById(id);
            if(dish.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        List<Long> setmealDishList=setmealDishAMapper.getById(ids);
        if(setmealDishList.size()>0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        ids.forEach(id->{
            dishMapper.delectByid(id);
            dishFlavorMapper.delectByid(id);
        });
    }

    @Override
    public DishVo getById(Long id) {
        Dish dish=dishMapper.getById(id);
        DishVo dishVo=new DishVo();
        List<DishFlavor> dishFlavorList=dishFlavorMapper.getById(id);
        BeanUtils.copyProperties(dish,dishVo);
        dishVo.setFlavors(dishFlavorList);
        return dishVo;
    }

    @Override
    public void update(DishDTO dishDTO) {
        //口味要删除再重新插入
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.update(dish);
        dishFlavorMapper.delectByid(dishDTO.getId());
        List<DishFlavor> dishFlavorList=dishDTO.getFlavors();
        if(dishFlavorList!=null&&dishFlavorList.size()>0){
            dishFlavorList.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertBatch(dishFlavorList);
        }
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish=Dish.builder()
                .id(id)
                .status(status)
                .build();
        dishMapper.update(dish);
    }
}
