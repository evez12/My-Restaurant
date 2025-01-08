package com.huseynov.restaurant.security;

public class CustomAuthException extends RuntimeException {
    public CustomAuthException(String message) {
        super(message);
    }
}
