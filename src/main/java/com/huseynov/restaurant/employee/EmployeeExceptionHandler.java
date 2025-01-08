package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.dto.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
class EmployeeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExistsEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleExistsEmailException(ExistsEmailException exception) {
        ApiError response = new ApiError(HttpStatus.BAD_REQUEST, exception);
        response.setMessage("Email already exists");
        return response;
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        ApiError response = new ApiError(HttpStatus.NOT_FOUND, exception);
        response.setMessage("Employee not found");
        return response;
    }

    @ExceptionHandler(EmployeeServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiError handleEmployeeServiceException(EmployeeServiceException exception) {
        ApiError response = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        response.setMessage("Employee service error");
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiError handleRuntimeException(RuntimeException exception) {
        ApiError response = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        response.setMessage("Runtime error");
        return response;
    }

}
