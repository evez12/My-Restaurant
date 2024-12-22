package com.huseynov.restaurant.shared.dto.request;

import com.huseynov.restaurant.shared.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateUserRequest {

    @NotBlank(message = "Name shouldn't be empty")
    String name;

    @NotBlank(message = "Surname shouldn't be empty")
    String surname;

    @NotBlank(message = "Email shouldn't be empty")
    @Size(min = 3, max = 100, message = "Email must be between 3 and 100 characters")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email pattern") // Email pattern (exp: a@b)
    String email;

    @Size(min = 4, message = "Password must be at least 4 characters")
    @NotBlank(message = "Password shouldn't be empty")
    String password;

    String address;

    @Size(max = 13, message = "Phone number must be max 13 characters")
    String phoneNumber;

    Gender gender = Gender.UNDEFINED;

    @Min(value = 0, message = "Salary must be positive")
    BigDecimal salary = BigDecimal.ZERO;
}
