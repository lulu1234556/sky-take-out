package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    // 订单状态：待付款
    public static final Integer PENDING_PAYMENT = 1;
    // 订单状态：待接单
    public static final Integer TO_BE_CONFIRMED = 2;
    // 订单状态：已接单
    public static final Integer CONFIRMED = 3;
    // 订单状态：派送中
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    // 订单状态：已完成
    public static final Integer COMPLETED = 5;
    // 订单状态：已取消
    public static final Integer CANCELLED = 6;

    // 支付状态：未支付
    public static final Integer UN_PAID = 0;
    // 支付状态：已支付
    public static final Integer PAID = 1;
    // 支付状态：退款
    public static final Integer REFUND = 2;

    private Long id;

    private String number;

    private Integer status;

    private Long userId;

    private Long addressBookId;

    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;

    private Integer payMethod;

    private Integer payStatus;

    private BigDecimal amount;

    private String remark;

    private String phone;

    private String address;

    private String consignee;

    private String cancelReason;

    private String rejectionReason;

    private LocalDateTime cancelTime;

    private LocalDateTime estimatedDeliveryTime;

    private Integer deliveryStatus;

    private LocalDateTime deliveryTime;

    private Integer packAmount;

    private Integer tablewareNumber;

    private Integer tablewareStatus;
}
