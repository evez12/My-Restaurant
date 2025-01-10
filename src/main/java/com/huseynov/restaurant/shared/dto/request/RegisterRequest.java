package com.huseynov.restaurant.shared.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huseynov.restaurant.shared.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Email is required")
    @Size(min = 3, max = 120, message = "Email must be between 3 and 120 characters")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 characters")
    private String password;

    private String name;
    private String surname;
    private Gender gender = Gender.UNDEFINED;
    private String phoneNumber;
    private String address;


    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
