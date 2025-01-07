package com.huseynov.restaurant.shared.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiValidationError extends ApiSubError {
    String field;
    String message;
    String object;
    Object rejectedValue;

    public ApiValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public ApiValidationError(String field, String message, Object rejectedValue) {
        this.field = field;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }



}
