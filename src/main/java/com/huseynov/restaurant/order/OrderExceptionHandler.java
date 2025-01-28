package com.huseynov.restaurant.order;

import com.huseynov.restaurant.shared.dto.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderExceptionHandler {

    @ExceptionHandler(OrderServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleOrderServiceException(OrderServiceException e) {
        log.error("OrderServiceException occurred: {}", e.getMessage());
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Order service error", e);
    }
}
