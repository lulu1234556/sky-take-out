package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@Api(tags = "工作台相关接口")
public class WorkSpaceController {
    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping("/businessData")
    @ApiOperation("查询今日运营数据")
    public Result<BusinessDataVO> getBussinessDate(){
        LocalDateTime begin=LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end=LocalDateTime.now().with(LocalTime.MAX);
        BusinessDataVO businessDataVO=workspaceService.getBussinessDate(begin,end);
        return Result.success(businessDataVO);
    }

    @GetMapping("/overviewOrders")
    @ApiOperation("查询订单管理数据")
    public Result<OrderOverViewVO> getOverviewOrders(){
        OrderOverViewVO orderOverViewVO=workspaceService.getOverviewOrders();
        return Result.success(orderOverViewVO);
    }

    @GetMapping("/overviewDishes")
    @ApiOperation("查询菜品总览")
    public Result<DishOverViewVO> getOverviewDishes(){
        DishOverViewVO dishOverViewVO=workspaceService.getOverviewDishes();
        return Result.success(dishOverViewVO);
    }

    @GetMapping("/overviewSetmeals")
    @ApiOperation("查询套餐总览")
    public Result<SetmealOverViewVO> getOverviewSetmeals(){
        SetmealOverViewVO setmealOverViewVO=workspaceService.getOverviewSetmeals();
        return Result.success(setmealOverViewVO);
    }
}
