package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;

    @Override
    @Transactional
    @CacheEvict(cacheNames = "setmeal", allEntries = true)
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.insert(setmeal);

        List<SetmealDish> setmealDishes=setmealDTO.getSetmealDishes();
        if(setmealDishes!=null&&setmealDishes.size()>0){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmeal.getId());
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }

    }

    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVo> page=setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "setmeal", allEntries = true)
    public void delete(List<Long> ids) {
        //setmeal中菜品删除，对应setmeal中数据也应该删除
        for(Long id:ids){
            Setmeal setmeal=setmealMapper.getById(id);
            if(setmeal.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        for(Long id:ids){
            setmealMapper.deleteById(id);
            setmealDishMapper.deleteById(id);
        }
    }

    @Override
    public SetmealVo getById(Long id) {
        SetmealVo setmealVo=setmealMapper.getByIdWithDish(id);
        return setmealVo;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "setmeal", allEntries = true)
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);

        setmealDishMapper.getBySetmealId(setmealDTO.getId());
        List<SetmealDish> setmealDishes=setmealDTO.getSetmealDishes();
        if(!setmealDishes.isEmpty()){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealDTO.getId());
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    @Override
    @CacheEvict(cacheNames = "setmeal", allEntries = true)
    public void startOrStop(Integer status, Long id) {
        if (status == StatusConstant.ENABLE) {
            Integer count = dishMapper.countBySetmealIdAndStatus(id, StatusConstant.DISABLE);
            if (count > 0) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        }
        Setmeal setmeal=Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.update(setmeal);
    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> setmeals=setmealMapper.list(setmeal);
        return setmeals;
    }

    @Override
    public List<DishItemVO> listInSetmeal(Long id) {
        List<DishItemVO> dishItemVOs=setmealMapper.listInSetmeal(id);
        return dishItemVOs;
    }
}
