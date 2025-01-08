package com.huseynov.restaurant.shared.exception;

import com.huseynov.restaurant.shared.dto.error.ApiError;
import com.huseynov.restaurant.shared.dto.error.ApiValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentException(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException: {}", exception.getMessage());
        ApiError response = new ApiError(HttpStatus.BAD_REQUEST, "Validation error", exception);
        List<ApiValidationError> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiValidationError(error.getField(), error.getDefaultMessage(), error.getRejectedValue()))
                .toList();

        response.getSubErrors().addAll(errors);

        log.error("MethodArgumentNotValidException response: {}", response);
        return response;
    }

}
