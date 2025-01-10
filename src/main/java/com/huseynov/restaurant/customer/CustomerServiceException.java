package com.huseynov.restaurant.customer;

public class CustomerServiceException extends RuntimeException {
    public CustomerServiceException(String s, RuntimeException e) {
        super(s, e);
    }

    public CustomerServiceException(String message) {
        super(message);
    }
}
