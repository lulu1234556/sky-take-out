package com.sky.exception;

public class OrderBusinessException extends RuntimeException {
    public OrderBusinessException() {
    }

    public OrderBusinessException(String msg) {
        super(msg);
    }
}
