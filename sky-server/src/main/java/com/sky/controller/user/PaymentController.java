package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.result.Result;
import com.sky.service.PaymentService;
import com.sky.vo.OrderPaymentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("userPaymentController")
@RequestMapping("/user/order")
@Api(tags = "C端-订单支付接口")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO){
        OrderPaymentVO ordersPaymentVO=paymentService.payment(ordersPaymentDTO);
        return Result.success(ordersPaymentVO);
    }
}
