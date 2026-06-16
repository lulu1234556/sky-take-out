package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public BusinessDataVO getBussinessDate(LocalDateTime begin, LocalDateTime end) {
        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        Integer totalOrderCount=orderMapper.countByMap(map);
        Integer newUsers=userMapper.countByMap(map);
        map.put("status", Orders.COMPLETED);
        Integer validOrderCount=orderMapper.countByMap(map);
        Double orderCompletionRate=0.0;
        Double unitPrice=0.0;
        Double turnover=orderMapper.sumByMap(map);
        turnover = turnover == null ? 0.0 : turnover;
        if(totalOrderCount != 0 && validOrderCount != 0){
            orderCompletionRate=validOrderCount*1.0/totalOrderCount;
            unitPrice=turnover/validOrderCount;
        }
        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    @Override
    public OrderOverViewVO getOverviewOrders() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));//只统计今天 00:00 之后的订单
        Integer allOrders=orderMapper.countByMap(map);
        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer waitingOrders=orderMapper.countByMap(map);
        map.put("status",Orders.CONFIRMED);
        Integer deliveredOrders=orderMapper.countByMap(map);
        map.put("status",Orders.COMPLETED);
        Integer completedOrders=orderMapper.countByMap(map);
        map.put("status",Orders.CANCELLED);
        Integer cancelledOrders=orderMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .cancelledOrders(cancelledOrders)
                .completedOrders(completedOrders)
                .allOrders(allOrders)
                .build();

    }

    @Override
    public DishOverViewVO getOverviewDishes() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold=dishMapper.countByMap(map);
        map.put("status",StatusConstant.DISABLE);
        Integer discontinued=dishMapper.countByMap(map);
        return DishOverViewVO.builder()
                .discontinued(discontinued)
                .sold(sold)
                .build();
    }

    @Override
    public SetmealOverViewVO getOverviewSetmeals() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold=setmealMapper.countByMap(map);
        map.put("status",StatusConstant.DISABLE);
        Integer discontinued=setmealMapper.countByMap(map);
        return SetmealOverViewVO.builder()
                .discontinued(discontinued)
                .sold(sold)
                .build();
    }
}
