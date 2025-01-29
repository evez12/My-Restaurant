package com.huseynov.restaurant.product;

import com.huseynov.restaurant.shared.dto.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ProductExceptionHandler {

    @ExceptionHandler(ProductServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError productServiceExceptionHandle(ProductServiceException exception) {
        log.error("ProductExceptionHandler:productServiceExceptionHandle occurred, exceptoin message: {}"
                , exception.getMessage());
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Product service error", exception);
    }
}
