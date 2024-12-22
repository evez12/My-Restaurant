package com.huseynov.restaurant.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huseynov.restaurant.shared.dto.ErrorDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    String status;
    List<ErrorDTO> errors;
    T results;


}
