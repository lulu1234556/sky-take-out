package com.sky.exception;

public class AddressBookBusinessException extends RuntimeException {
    public AddressBookBusinessException(String message) {
        super(message);
    }
}
