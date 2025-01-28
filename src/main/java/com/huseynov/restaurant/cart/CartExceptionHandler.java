package com.huseynov.restaurant.cart;

import com.huseynov.restaurant.shared.dto.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CartExceptionHandler {

    @ExceptionHandler(CartServiceException.class)
    public ApiError cartServiceExceptionHandle(CartServiceException exception) {
        log.error("CartExceptionHandler:cartServiceExceptionHandle execution started");
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Cart service error", exception);
    }
}
