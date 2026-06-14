package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    PageResult list(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderVO getOrderDetailById(Long id);

    void cancelById(Long id);

    void repetition(Long id);
}
