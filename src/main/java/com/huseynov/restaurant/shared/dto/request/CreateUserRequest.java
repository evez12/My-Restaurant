package com.huseynov.restaurant.shared.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {  // both of for Employee and Customer

    String name;
    String surname;
    String email;
    String password;
}
