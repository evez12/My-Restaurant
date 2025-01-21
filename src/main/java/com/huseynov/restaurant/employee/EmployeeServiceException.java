package com.huseynov.restaurant.employee;

class EmployeeServiceException extends RuntimeException {
    EmployeeServiceException(String message) {
        super(message);
    }

    EmployeeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
