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

@RestControllerAdvice
@Slf4j()
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

    @ExceptionHandler(ExistsItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleExistsItemException(ExistsItemException exception) {
        log.error("ExistsItemException: {}", exception.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST, "Already exists", exception);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError handleEmployeeNotFoundException(CustomNotFoundException exception) {
        log.error("CustomNotFoundException: {}", exception.getMessage());
        return new ApiError(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(CustomAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleCustomAuthException(CustomAuthException exception) {
        log.error("CustomAuthException: {}", exception.getMessage());
        return new ApiError(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidRequestException(InvalidRequestException exception) {
        log.error("InvalidRequestException: {}", exception.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

}
