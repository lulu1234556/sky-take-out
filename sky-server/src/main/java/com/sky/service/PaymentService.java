package com.sky.service;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.vo.OrderPaymentVO;

public interface PaymentService {
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO);
    void paySuccess(String outTradeNo);
}
