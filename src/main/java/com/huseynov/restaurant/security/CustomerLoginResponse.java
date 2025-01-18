package com.huseynov.restaurant.security;

import com.huseynov.restaurant.shared.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class CustomerLoginResponse {
    private Long id;
    private String email;
    private String password;
    private Set<Role> roles;


}
