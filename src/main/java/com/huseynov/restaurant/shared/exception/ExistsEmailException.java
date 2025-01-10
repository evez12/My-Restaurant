package com.huseynov.restaurant.shared.exception;

public class ExistsEmailException extends RuntimeException {
    public ExistsEmailException(String message) {
        super(message);
    }

    public ExistsEmailException() {
    }
}
