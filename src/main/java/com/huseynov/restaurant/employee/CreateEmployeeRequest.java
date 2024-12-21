package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateEmployeeRequest {

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String name;

    @NotNull(message = "Surname is required")
    @Size(min = 3, max = 50, message = "Surname must be between 3 and 50 characters")
    String surname;

    @NotNull(message = "Email is required")
    @Size(min = 3, max = 70, message = "Email must be between 3 and 70 characters")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email pattern")
    String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotNull(message = "Password is required")
    String password;

    String address;

    @Size(max = 13, message = "Phone number must be max 13 characters")
    String phoneNumber;

    Gender gender=Gender.UNDEFINED;

    @Min(value = 0, message = "Salary must be positive")
    BigDecimal salary = BigDecimal.ZERO;
}
