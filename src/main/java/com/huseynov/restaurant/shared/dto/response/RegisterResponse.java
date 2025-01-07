package com.huseynov.restaurant.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class RegisterResponse {
    private String token;
    private String email;
    private List<String> roles;

    public RegisterResponse(String email, String token, List<String> roles) {
        this.roles = roles;
        this.token = token;
        this.email = email;
    }
}
