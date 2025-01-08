package com.huseynov.restaurant.customer;

import lombok.Getter;

@Getter
public class InvalidEmailException extends RuntimeException {
    private final String rejectedValue;

    public InvalidEmailException(String message, String rejectedValue) {
        super(message);
        this.rejectedValue = rejectedValue;
    }

    public InvalidEmailException(String message, Throwable cause, String rejectedValue) {
        super(message, cause);
        this.rejectedValue = rejectedValue;
    }

}
