package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.dto.ErrorDTO;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
class EmployeeExceptionHandler {
    private static final String FAILED_MESSAGE = "FAILED";

    @ExceptionHandler(ExistsEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiResponse<List<ErrorDTO>> handleExistsEmailException(ExistsEmailException exception) {
        ApiResponse<List<ErrorDTO>> response = new ApiResponse<>();
        response.setStatus(FAILED_MESSAGE);
        response.setErrors(Collections.singletonList(new ErrorDTO("email", exception.getMessage())));
        return response;
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiResponse<List<ErrorDTO>> handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        ApiResponse<List<ErrorDTO>> response = new ApiResponse<>();
        response.setStatus(FAILED_MESSAGE);
        response.setErrors(Collections.singletonList(new ErrorDTO("id", exception.getMessage())));
        return response;
    }

    @ExceptionHandler(EmployeeServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiResponse<List<ErrorDTO>> handleEmployeeServiceException(EmployeeServiceException exception) {
        ApiResponse<List<ErrorDTO>> response = new ApiResponse<>();
        response.setStatus(FAILED_MESSAGE);
        response.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiResponse<List<ErrorDTO>> handleRuntimeException(RuntimeException exception) {
        ApiResponse<List<ErrorDTO>> response = new ApiResponse<>();
        response.setStatus(FAILED_MESSAGE);
        response.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return response;
    }

}
