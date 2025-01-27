package com.huseynov.restaurant.employee.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmployeeDetailDTO {
    Long detailId;
    String address;
    boolean enabled = Boolean.TRUE;
    BigDecimal salary = BigDecimal.ZERO;
    String phoneNumber;
    String gender;
}
