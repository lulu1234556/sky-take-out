package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
@RestController
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    @CacheEvict(cacheNames = "dish", allEntries = true)
    public Result addDish(@RequestBody  DishDTO dishDTO){
        log.info("新增菜品:{}",dishDTO);
        dishService.addDish(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询：{}",dishPageQueryDTO);
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        log.info("根据分类id查询菜品：{}", categoryId);
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    @DeleteMapping
    @ApiOperation("批量删除菜品")
    @CacheEvict(cacheNames = "dish", allEntries = true)
    public Result delectBatch(@RequestParam List<Long> ids){
        log.info("批量删除菜品：{}",ids);
        dishService.delectBatch(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVo> getById(@PathVariable Long id){
        log.info("根据id查询菜品:{}",id);
        DishVo dishVo= dishService.getById(id);
        return Result.success(dishVo);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    @CacheEvict(cacheNames = "dish", allEntries = true)
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}",dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("起售与停售")
    @CacheEvict(cacheNames = "dish", allEntries = true)
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("菜品起售、停售：{},{}",status,id);
        dishService.startOrStop(status,id);
        return Result.success();
    }
}
