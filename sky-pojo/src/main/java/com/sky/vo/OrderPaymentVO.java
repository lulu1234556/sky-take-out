package com.sky.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPaymentVO implements Serializable {

    private String nonceStr;

    private String paySign;

    private String timeStamp;

    private String signType;

    private String packageStr;
}
