package com.huseynov.restaurant.shared.config;

import com.huseynov.restaurant.customer.Customer;
import com.huseynov.restaurant.security.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class EntityConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // For the customer who sent the request, the customer id is saved
    // Bean have been will used (exp: CartServiceImpl...)
    @Bean
    @RequestScope
    public Customer myCustomer(UserDetailsServiceImpl userDetailsService) {
        return userDetailsService.getMyCustomer();
    }
}
