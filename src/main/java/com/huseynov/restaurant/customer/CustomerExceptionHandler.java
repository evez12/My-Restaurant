package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.shared.dto.error.ApiError;
import com.huseynov.restaurant.shared.dto.error.ApiValidationError;
import com.huseynov.restaurant.shared.exception.InvalidEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomerExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidEmailException(InvalidEmailException exception) {
        log.info("Handling InvalidEmailException: {}", exception.getMessage());
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Validation errors", exception);
        ApiValidationError subError = new ApiValidationError("email", exception.getMessage(), exception.getRejectedValue());
        error.getSubErrors().add(subError);
        return error;
    }

    @ExceptionHandler(CustomerServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleCustomerServiceException(CustomerServiceException exception) {
        log.info("Handling CustomerServiceException: {}", exception.getMessage());
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }
}
