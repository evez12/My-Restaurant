package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.shared.dto.error.ApiError;
import com.huseynov.restaurant.shared.dto.error.ApiValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidEmailException(InvalidEmailException exception) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Validation errors", exception);
        ApiValidationError subError = new ApiValidationError("email", exception.getMessage(), exception.getRejectedValue());
        error.getSubErrors().add(subError);
        return error;
    }
}
