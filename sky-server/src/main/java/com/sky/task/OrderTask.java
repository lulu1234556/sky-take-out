package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron ="0 * * * * ?")
    public void processTimeOut(){
        log.info("开始进行支付超时订单处理：{}", LocalDateTime.now());
        LocalDateTime time=LocalDateTime.now().plusMinutes(-15);//再往前推。十五分钟内还没支付的订单
        List<Orders> ordersList=orderMapper.getByStatusAndOrdertimeLT(Orders.PENDING_PAYMENT,time);
        if(ordersList!=null &&ordersList.size()>0){
            ordersList.forEach(order->{
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("支付超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            });
        }
    }

    @Scheduled(cron ="0 0 1 * * ?")
    public void processDeliveryOrder(){
        log.info("开始进行未完成订单状态处理：{}", LocalDateTime.now());
        LocalDateTime time=LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList=orderMapper.getByStatusAndOrdertimeLT(Orders.DELIVERY_IN_PROGRESS,time);
        if(ordersList!=null && ordersList.size()>0){
            ordersList.forEach(order -> {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            });
        }
    }
}
