package com.huseynov.restaurant.shared.exception;

public class ExistsItemException extends RuntimeException {
    public ExistsItemException(String message) {
        super(message);
    }

    public ExistsItemException() {
    }
}
