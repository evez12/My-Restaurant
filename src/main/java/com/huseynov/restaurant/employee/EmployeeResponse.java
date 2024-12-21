package com.huseynov.restaurant.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huseynov.restaurant.shared.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmployeeResponse {

    Long employeeId;
    String name;
    String surname;
    String email;
    String address;
    Gender gender = Gender.OTHER;
    BigDecimal salary = BigDecimal.ZERO;
    String phoneNumber;

}
