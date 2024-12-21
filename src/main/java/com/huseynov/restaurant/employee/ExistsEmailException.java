package com.huseynov.restaurant.employee;

public class ExistsEmailException extends RuntimeException {
    public ExistsEmailException() {
    }

    public ExistsEmailException(String message) {
        super(message);
    }

    public ExistsEmailException(String message, Throwable cause) {
        super(message, cause);
    }

}
