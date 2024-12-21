package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.dto.Error;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import com.huseynov.restaurant.shared.enums.StatusMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(ExistsEmailException.class)
    ResponseEntity<ApiResponse<?>> handleExistsEmailException(ExistsEmailException exception) {
        ApiResponse<?> response = new ApiResponse<>();
        response.setStatus(StatusMessage.FAILED.toString());
        response.setErrors(Collections.singletonList(new Error("email", exception.getMessage())));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException exception) {
        ApiResponse<?> response = new ApiResponse<>();
        response.setStatus(StatusMessage.FAILED.toString());
        response.setErrors(Collections.singletonList(new Error("", exception.getMessage())));
        return ResponseEntity.badRequest().body(response);
    }

}
