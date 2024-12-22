package com.huseynov.restaurant.shared.exception;

import com.huseynov.restaurant.shared.dto.ErrorDTO;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
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
    private static final String FAILED_MESSAGE = "FAILED";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<List<ErrorDTO>> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        ApiResponse<List<ErrorDTO>> response = new ApiResponse<>();
        log.warn("MethodArgumentNotValidException: {}", exception.getMessage());
        // added field and message to the ErrorDTO
        // added ErrorDTO to the List
        List<ErrorDTO> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorDTO(error.getField(), error.getDefaultMessage()))
                .toList();

        response.setStatus(FAILED_MESSAGE);
        response.setErrors(errors);
        log.warn("MethodArgumentNotValidException response: {}", response);
        return response;
    }

}
