package com.huseynov.restaurant.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
@NoArgsConstructor
public class ApiResponse<T> {
    String status;
    T results;

    public ApiResponse(String status, T results) {
        this.status = status;
        this.results = results;
    }

}
